package com.kbds.minipay.service;

import com.kbds.minipay.domain.Account;
import com.kbds.minipay.domain.User;
import com.kbds.minipay.dto.user.UserCreateRequest;
import com.kbds.minipay.repository.AccountRepository;
import com.kbds.minipay.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long userCreate(UserCreateRequest userCreateRequest){
        User user = userCreateRequest.toEntity();
        userRepository.save(user);

        Account account = Account.builder()
                .user(user)
                .name("")
                .isMain(true)
                .isSaving(false)
                .build();

        accountRepository.save(account);
        return user.getId();
    }
}
