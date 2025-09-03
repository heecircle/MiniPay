package com.kbds.minipay.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChargeRequest {
	String accountNumber;
	Integer chargeAmount;
}
