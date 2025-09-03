package com.kbds.minipay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbds.minipay.domain.UserChargeLimit;

public interface UserChargeLimitRepository extends JpaRepository<UserChargeLimit, Long> {

	UserChargeLimit findFirstByAccountNumberAndChargeDate(String accountNumber, LocalDate chargeDate);

	List<UserChargeLimit> findAllByChargeDateBefore(LocalDate chargeDate);

}
