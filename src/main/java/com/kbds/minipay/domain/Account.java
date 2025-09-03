package com.kbds.minipay.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.boot.model.naming.IllegalIdentifierException;

import com.kbds.minipay.common.exception.NotAvailableException;

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
	private String accountNumber;

	@Column
	@ColumnDefault(value = "0")
	private Double interestRate;

	@Column
	@ColumnDefault(value = "0")
	private Integer balance;

	@Builder
	public Account(User user, String name, boolean isMain, double interestRate, boolean isSaving, int balance,
		String password, String accountNumber) {
		this.user = user;
		this.balance = balance;
		this.name = name;
		this.isMain = isMain;
		this.interestRate = interestRate;
		this.isSaving = isSaving;
		this.active = true;
		this.accountNumber = accountNumber;
		this.password = password;
	}

	public void isOwner(User user, String password) throws RuntimeException {
		if (user.getId() == this.user.getId() && password.equals(this.password)) {
			return;
		}
		throw new NotAvailableException("잘못된 비밀번호 입니다.");
	}

	public void withdrawAvailable(int amount) {
		if (this.balance >= amount) {
			return;
		}
		throw new NotAvailableException("출금 한도가 초과되었습니다.");
	}

	public Long getIdByPassword(String password) {
		if (password.equals(this.password)) {
			return id;
		}
		return null;
	}

	public void setAmount(int amount, boolean isWithdraw) {
		if (isWithdraw) {
			this.balance -= amount;
		} else {
			this.balance += amount;
		}
	}

	public void isMain() {
		if (!this.isMain) {
			throw new IllegalIdentifierException("메인 계좌가 아닌 돈에서 출금은 불가능 합니다.");
		}
	}
}
