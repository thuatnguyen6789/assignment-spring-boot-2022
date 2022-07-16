package com.example.springbootthuatdev.repository;

import com.example.springbootthuatdev.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
