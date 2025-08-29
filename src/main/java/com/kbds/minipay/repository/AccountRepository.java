package com.kbds.minipay.repository;

import com.kbds.minipay.domain.Account;
import jdk.jfr.Registered;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    @NonNull Optional<Account> findById(@NonNull  Long id);
}
