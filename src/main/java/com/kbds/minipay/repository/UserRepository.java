package com.kbds.minipay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbds.minipay.domain.User;

import lombok.NonNull;

public interface UserRepository extends JpaRepository<User, Long> {

	@NonNull
	Optional<User> findById(@NonNull Long id);

	@NonNull
	Optional<User> findByResidenceNumber(@NonNull String residenceNumber);
}
