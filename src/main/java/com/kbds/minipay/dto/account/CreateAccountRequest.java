package com.kbds.minipay.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccountRequest {
	Long userId;
	String name;
	String password;
	int interestRate;
}
