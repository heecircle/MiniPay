package com.kbds.minipay.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String residenceNumber;

    @Column
    private String email;

    @Column
    private String phoneNumber;


    @Builder
    public User(String name, String residenceNumber, String email, String phoneNumber) {
        this.name = name;
        this.residenceNumber = residenceNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
