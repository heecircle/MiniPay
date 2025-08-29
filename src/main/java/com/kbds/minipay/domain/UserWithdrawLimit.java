package com.kbds.minipay.domain;

import com.kbds.minipay.common.domain.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class UserWithdrawLimit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
     private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column
    private Integer limit;

}
