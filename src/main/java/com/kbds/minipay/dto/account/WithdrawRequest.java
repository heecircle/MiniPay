package com.kbds.minipay.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawRequest {
	String residenceNumber;
	String accountNumber;
	String putAccountNumber;
	int withdrawAmount;
	String password;
}
