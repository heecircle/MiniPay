package com.kbds.minipay.domain;

import java.time.LocalDate;

import com.kbds.minipay.common.domain.BaseTimeEntity;
import com.kbds.minipay.common.exception.BalanceException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserWithdrawLimit extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@Column
	private Integer withdrawLimit;

	@Column
	private LocalDate withdrawDate;

	@Builder
	public UserWithdrawLimit(User user, Account account) {
		this.user = user;
		this.account = account;
		this.withdrawDate = LocalDate.now();
		this.withdrawLimit = 300_000;
	}

	public void isWithdrawAvailable(int amount) throws BalanceException {
		if (this.withdrawLimit >= amount) {
			return;
		}
		throw new BalanceException("잔액이 부족합니다.");
	}

	public Long withdrawLimit(int amount) {
		this.withdrawLimit -= amount;
		return this.id;
	}

}
