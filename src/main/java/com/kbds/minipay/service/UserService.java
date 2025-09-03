package com.kbds.minipay.service;

import static com.kbds.minipay.service.AccountNumberMaker.*;

import org.springframework.stereotype.Service;

import com.kbds.minipay.domain.Account;
import com.kbds.minipay.domain.User;
import com.kbds.minipay.dto.user.UserCreateRequest;
import com.kbds.minipay.repository.AccountRepository;
import com.kbds.minipay.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;

	@Transactional
	public String userCreate(UserCreateRequest userCreateRequest) {
		// User 생성
		User user = userCreateRequest.toEntity();
		userRepository.save(user);

		String accountNumber = makeAccountNumber();

		// user의 Account 생성
		Account account = Account.builder()
			.user(user)
			.name(userCreateRequest.getName())
			.accountNumber(accountNumber)
			.isMain(true)
			.isSaving(false)
			.password(userCreateRequest.getPassword())
			.build();

		accountRepository.save(account);
		return accountNumber;
	}

}
