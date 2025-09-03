package com.kbds.minipay.service;

import java.util.Random;
import java.util.random.RandomGenerator;

import com.kbds.minipay.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountNumberMaker {
	private static AccountRepository accountRepository;

	public static String makeAccountNumber() {
		Random random = Random.from(RandomGenerator.getDefault());
		String accountNumber = "";
		do {
			accountNumber = random.nextInt(10000) + "-" + random.nextInt(10000) + "-" + random.nextInt(10000);
		} while (accountRepository.findByAccountNumber(accountNumber).isPresent());
		return accountNumber;
	}
}
