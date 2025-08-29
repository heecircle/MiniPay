package com.kbds.minipay.repository;

import com.kbds.minipay.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @NonNull Optional<User> findById(@NonNull Long id);
}
