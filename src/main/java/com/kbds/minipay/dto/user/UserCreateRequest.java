package com.kbds.minipay.dto.user;

import com.kbds.minipay.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

    String name;

    String residenceNumber;

    String email;

    String phoneNumber;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
