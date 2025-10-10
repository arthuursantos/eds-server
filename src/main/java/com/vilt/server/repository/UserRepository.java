package com.vilt.server.repository;

import com.vilt.server.domain.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
}
