package com.kbds.minipay.repository;

import com.kbds.minipay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
