package com.kbds.minipay.service;

import static com.kbds.minipay.service.AccountNumberMaker.*;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.kbds.minipay.common.exception.BalanceException;
import com.kbds.minipay.common.exception.NotFoundException;
import com.kbds.minipay.domain.Account;
import com.kbds.minipay.domain.User;
import com.kbds.minipay.domain.UserChargeLimit;
import com.kbds.minipay.domain.UserWithdrawLimit;
import com.kbds.minipay.dto.account.ChargeRequest;
import com.kbds.minipay.dto.account.CreateAccountRequest;
import com.kbds.minipay.dto.account.WithdrawRequest;
import com.kbds.minipay.repository.AccountRepository;
import com.kbds.minipay.repository.UserChargeLimitRepository;
import com.kbds.minipay.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;

	private final UserChargeLimitRepository userChargeLimitRepository;
	private final int USER_LIMIT_CHARGE_AMOUNT = 3_000_000;

	public String chargeAmount(ChargeRequest chargeRequest) throws BalanceException {
		int amount = chargeRequest.getChargeAmount();
		Account account = accountRepository.findByAccountNumber(chargeRequest.getAccountNumber())
			.orElseThrow(() -> new NotFoundException("존재하지 않는 계좌입니다."));

		UserChargeLimit userChargeLimit = userChargeLimitRepository.findFirstByAccountNumberAndChargeDate(
			chargeRequest.getAccountNumber(), LocalDate.now());
		if (userChargeLimit == null) {
			userChargeLimit = UserChargeLimit.builder()
				.accountNumber(chargeRequest.getAccountNumber())
				.amount(USER_LIMIT_CHARGE_AMOUNT)
				.build();
		}
		if (userChargeLimit.getAmount() < amount) {
			throw new BalanceException("충전 한도를 초과하였습니다.");
		}

		userChargeLimit.setAmount(userChargeLimit.getAmount() - amount);
		userChargeLimitRepository.save(userChargeLimit);

		return chargeRequest.getAccountNumber();

	}

	// public Long withdraw(WithdrawRequest withdrawRequest) throws BalanceException {
	// 	Account account = accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber()).orElseThrow(
	// 		() -> new NotFoundException("계좌정보를 다시 확인하세요."));
	//
	// 	User user = userRepository.findByResidenceNumber(withdrawRequest.getResidenceNumber())
	// 		.orElseThrow(() -> new NotFoundException("등록되지 않은 사용자 입니다."));
	//
	// 	// 본인만 메인계좌에서 인출 가능
	// 	account.isOwner(user, withdrawRequest.getPassword());
	// 	account.withdrawAvailable(withdrawRequest.getWithdrawAmount());
	// 	account.setAmount(withdrawRequest.getWithdrawAmount(), true);
	// 	return 1L;
	// }

	private UserWithdrawLimit userWithdrawCreate(Account account, User user) {
		return UserWithdrawLimit.builder()
			.user(user)
			.account(account)
			.build();
	}

	public Long createAccount(CreateAccountRequest createAccountRequest) {
		String residenceNumber = createAccountRequest.getResidenceNumber();
		User user = userRepository.findByResidenceNumber(residenceNumber).orElseThrow(
			() -> new NotFoundException("등록되지 않은 사용자 입니다, 메인 계좌 개설이 필요합니다.")
		);
		String accountNumber = makeAccountNumber();
		Account account = Account.builder()
			.accountNumber(accountNumber)
			.isMain(false)
			.password(createAccountRequest.getPassword())
			.interestRate(createAccountRequest.getInterestRate())
			.user(user)
			.build();
		accountRepository.save(account);
		return account.getIdByPassword(createAccountRequest.getPassword());

	}

	@Transactional
	public Long withdrawAccount2Account(WithdrawRequest withdrawRequest) throws BalanceException {
		Account withdrawAccount = accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())
			.orElseThrow(() -> new NotFoundException(""));
		Account insertAccount = accountRepository.findByAccountNumber(withdrawRequest.getPutAccountNumber())
			.orElseThrow(() -> new NotFoundException(""));
		User user = userRepository.findByResidenceNumber(withdrawRequest.getResidenceNumber())
			.orElseThrow(() -> new NotFoundException(""));

		withdrawAccount.isOwner(user, withdrawRequest.getPassword());
		withdrawAccount.withdrawAvailable(withdrawRequest.getWithdrawAmount());
		withdrawAccount.isMain();

		withdrawAccount.setAmount(withdrawRequest.getWithdrawAmount(), true);
		insertAccount.setAmount(withdrawRequest.getWithdrawAmount(), false);

		return insertAccount.getIdByPassword(withdrawRequest.getPassword());
	}

	public void chargeAccount(ChargeRequest chargeRequest) throws BalanceException {
		
	}

}
