package com.kbds.minipay.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawRequest {
    Long userId;
    Long accountId;

    int withdrawAmount;

    String password;
}
