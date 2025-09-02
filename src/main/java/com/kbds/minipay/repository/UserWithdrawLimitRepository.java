package com.kbds.minipay.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbds.minipay.domain.UserWithdrawLimit;

import lombok.NonNull;

public interface UserWithdrawLimitRepository extends JpaRepository<UserWithdrawLimit, Long> {
	@NonNull
	Optional<UserWithdrawLimit> findByUser_IdAndWithdrawDate(Long userId, LocalDate withdrawDate);
}
