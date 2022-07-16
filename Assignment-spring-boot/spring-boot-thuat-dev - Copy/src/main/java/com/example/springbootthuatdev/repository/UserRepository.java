package com.example.springbootthuatdev.repository;

import com.example.springbootthuatdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
