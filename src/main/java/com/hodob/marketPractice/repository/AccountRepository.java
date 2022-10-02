package com.hodob.marketPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hodob.marketPractice.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
