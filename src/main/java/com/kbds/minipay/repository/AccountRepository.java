package com.kbds.minipay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kbds.minipay.domain.Account;

import lombok.NonNull;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	@NonNull
	Optional<Account> findById(@NonNull Long id);

	Optional<Account> findByAccountNumber(@NonNull String accountNumber);
}
