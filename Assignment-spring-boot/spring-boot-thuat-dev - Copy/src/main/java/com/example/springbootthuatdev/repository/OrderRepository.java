package com.example.springbootthuatdev.repository;

import com.example.springbootthuatdev.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
}
