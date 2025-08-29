package com.kbds.minipay.domain;

import com.kbds.minipay.common.exception.BalanceException;
import com.kbds.minipay.common.exception.NotAvailableException;
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
    public Account(User user, String name, boolean isMain, double interestRate, boolean isSaving, int balance, String password) {
        this.user = user;
        this.balance = balance;
        this.name = name;
        this.isMain = isMain;
        this.interestRate = interestRate;
        this.isSaving = isSaving;
        this.active = true;
        this.password = password;
    }

    public void isOwner(User user, String password) throws RuntimeException{
        if(user.getId() == this.user.getId() && password.equals(this.password)){
            return;
        }
        throw new NotAvailableException("잘못된 비밀번호 입니다.");
    }

    public void withdrawAvailable(int amount){
        if(this.balance >= amount){
            return;
        }
        throw new NotAvailableException("출금 한도가 초과되었습니다.");
    }
}
