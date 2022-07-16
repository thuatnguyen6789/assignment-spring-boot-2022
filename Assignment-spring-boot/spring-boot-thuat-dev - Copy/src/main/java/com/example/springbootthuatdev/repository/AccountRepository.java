package com.example.springbootthuatdev.repository;

import com.example.springbootthuatdev.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByUsername(String username);
}
