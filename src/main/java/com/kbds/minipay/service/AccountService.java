package com.kbds.minipay.service;

import com.kbds.minipay.common.exception.BalanceException;
import com.kbds.minipay.common.exception.NotFoundException;
import com.kbds.minipay.domain.Account;
import com.kbds.minipay.domain.User;
import com.kbds.minipay.domain.UserWithdrawLimit;
import com.kbds.minipay.dto.account.WithdrawRequest;
import com.kbds.minipay.repository.AccountRepository;
import com.kbds.minipay.repository.UserRepository;
import com.kbds.minipay.repository.UserWithdrawLimitRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserWithdrawLimitRepository userWithdrawLimitRepository;


    public int withdraw(WithdrawRequest withdrawRequest) throws BalanceException {
        Account account = accountRepository.findById(withdrawRequest.getAccountId()).orElseThrow(
                () -> new NotFoundException("계좌정보를 다시 확인하세요."));

        User user = userRepository.findById(withdrawRequest.getAccountId()).orElseThrow(() -> new NotFoundException("등록되지 않은 사용자 입니다."));

        account.isOwner(user, withdrawRequest.getPassword());

        account.withdrawAvailable(withdrawRequest.getWithdrawAmount());


        UserWithdrawLimit userWithdrawLimit = userWithdrawLimitRepository.findByUser_IdAndWithdrawDate(user.getId(), LocalDate.now()).orElse(
           null
        );
        if(userWithdrawLimit == null){
            userWithdrawLimit = userWithdrawCreate(account, user);
            userWithdrawLimitRepository.save(userWithdrawLimit);
        }

        userWithdrawLimit.isWithdrawAvailable(withdrawRequest.getWithdrawAmount());

        userWithdrawLimit.withdrawLimit(withdrawRequest.getWithdrawAmount());

        userWithdrawLimitRepository.save(userWithdrawLimit);


        return userWithdrawLimit.withdrawLimit(withdrawRequest.getWithdrawAmount());

    }

    private UserWithdrawLimit userWithdrawCreate(Account account, User user){
        return UserWithdrawLimit.builder()
                        .user(user)
                        .account(account)
                        .build();
    }

}
