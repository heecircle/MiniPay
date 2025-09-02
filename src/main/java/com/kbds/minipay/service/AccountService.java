package com.kbds.minipay.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.kbds.minipay.common.exception.BalanceException;
import com.kbds.minipay.common.exception.NotFoundException;
import com.kbds.minipay.domain.Account;
import com.kbds.minipay.domain.User;
import com.kbds.minipay.domain.UserWithdrawLimit;
import com.kbds.minipay.dto.account.CreateAccountRequest;
import com.kbds.minipay.dto.account.WithdrawRequest;
import com.kbds.minipay.repository.AccountRepository;
import com.kbds.minipay.repository.UserRepository;
import com.kbds.minipay.repository.UserWithdrawLimitRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final UserWithdrawLimitRepository userWithdrawLimitRepository;

	public Long withdraw(WithdrawRequest withdrawRequest) throws BalanceException {
		Account account = accountRepository.findById(withdrawRequest.getAccountId()).orElseThrow(
			() -> new NotFoundException("계좌정보를 다시 확인하세요."));

		User user = userRepository.findById(withdrawRequest.getAccountId())
			.orElseThrow(() -> new NotFoundException("등록되지 않은 사용자 입니다."));

		account.isOwner(user, withdrawRequest.getPassword());

		account.withdrawAvailable(withdrawRequest.getWithdrawAmount());

		UserWithdrawLimit userWithdrawLimit = userWithdrawLimitRepository.findByUser_IdAndWithdrawDate(user.getId(),
			LocalDate.now()).orElse(
			null
		);
		if (userWithdrawLimit == null) {
			userWithdrawLimit = userWithdrawCreate(account, user);
			userWithdrawLimitRepository.save(userWithdrawLimit);
		}

		userWithdrawLimit.isWithdrawAvailable(withdrawRequest.getWithdrawAmount());

		userWithdrawLimit.withdrawLimit(withdrawRequest.getWithdrawAmount());

		userWithdrawLimitRepository.save(userWithdrawLimit);

		return userWithdrawLimit.withdrawLimit(withdrawRequest.getWithdrawAmount());

	}

	private UserWithdrawLimit userWithdrawCreate(Account account, User user) {
		return UserWithdrawLimit.builder()
			.user(user)
			.account(account)
			.build();
	}

	public Long createAccount(CreateAccountRequest createAccountRequest) {
		Long userId = createAccountRequest.getUserId();
		User user = userRepository.findById(userId).orElseThrow(
			() -> new NotFoundException("잘못된 사용자 입니다.")
		);
		Account account = Account.builder()
			.name(createAccountRequest.getName())
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
		Account withdrawAccount = accountRepository.findById(withdrawRequest.getAccountId())
			.orElseThrow(() -> new NotFoundException(""));
		Account insertAccount = accountRepository.findById(withdrawRequest.getPutAccountId())
			.orElseThrow(() -> new NotFoundException(""));
		User user = userRepository.findById(withdrawRequest.getPutAccountId())
			.orElseThrow(() -> new NotFoundException(""));
		withdrawAccount.isOwner(user, withdrawRequest.getPassword());
		withdrawAccount.withdrawAvailable(withdrawRequest.getWithdrawAmount());

		withdrawAccount.setAmount(withdrawRequest.getWithdrawAmount(), true);
		insertAccount.setAmount(withdrawRequest.getWithdrawAmount(), false);

		return insertAccount.getIdByPassword(withdrawRequest.getPassword());
	}

}
