package com.kbds.minipay.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChargeLimit {

	@Column
	LocalDate chargeDate;

	@Column
	@Getter
	@Setter
	int amount;

	@Column
	String accountNumber;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder
	public UserChargeLimit(int amount, String accountNumber) {
		chargeDate = LocalDate.now();
		this.amount = amount;
		this.accountNumber = accountNumber;
	}
}
