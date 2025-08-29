package com.kbds.minipay.domain;

import com.kbds.minipay.repository.UserRepository;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private Boolean active;

    @Column
    private Boolean isMain;

    @Column
    private Boolean isSaving;


    @Column
    private Double interestRate;

    @Column
    private Integer balance;

    @Builder
    public Account(User user, String name, boolean isMain, double interestRate, boolean isSaving, int balance) {
        this.user = user;
        this.balance = balance;
        this.name = name;
        this.isMain = isMain;
        this.interestRate = interestRate;
        this.isSaving = isSaving;
        this.active = true;
    }


}
