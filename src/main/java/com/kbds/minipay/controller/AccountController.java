package com.kbds.minipay.controller;

import com.kbds.minipay.common.exception.BalanceException;
import com.kbds.minipay.dto.account.WithdrawRequest;
import com.kbds.minipay.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/withdraw")
    public int withdraw(@RequestBody WithdrawRequest withdrawRequest) throws BalanceException {
        System.out.println(accountService);
        return accountService.withdraw(withdrawRequest);
    }
}
