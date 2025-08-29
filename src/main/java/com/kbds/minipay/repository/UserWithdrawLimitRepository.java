package com.kbds.minipay.repository;

import com.kbds.minipay.domain.UserWithdrawLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWithdrawLimitRepository extends JpaRepository<UserWithdrawLimit, Long> {
}
