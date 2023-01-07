package com.zerobase.zerobaseprojectboard.repository;

import com.zerobase.zerobaseprojectboard.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
