package com.kbds.minipay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbds.minipay.common.exception.BalanceException;
import com.kbds.minipay.dto.account.ChargeRequest;
import com.kbds.minipay.dto.account.CreateAccountRequest;
import com.kbds.minipay.dto.account.WithdrawRequest;
import com.kbds.minipay.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@PostMapping("/charge")
	public String charge(@RequestBody ChargeRequest chargeRequest) throws BalanceException {
		return accountService.chargeAmount(chargeRequest);
	}

	@PostMapping("/create")
	public Long createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
		return accountService.createAccount(createAccountRequest);
	}

	@PostMapping("/sendAccount")
	public Long withdraw(@RequestBody WithdrawRequest withdrawRequest) throws BalanceException {
		return accountService.withdrawAccount2Account(withdrawRequest);
	}

}
