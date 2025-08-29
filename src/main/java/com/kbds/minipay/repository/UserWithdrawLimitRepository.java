package com.kbds.minipay.repository;

import com.kbds.minipay.domain.UserWithdrawLimit;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserWithdrawLimitRepository extends JpaRepository<UserWithdrawLimit, Long> {
    @NonNull Optional<UserWithdrawLimit> findByUser_IdAndWithdrawDate(Long userId, LocalDate withdrawDate);
}
