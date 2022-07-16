package com.example.springbootthuatdev.repository;

import com.example.springbootthuatdev.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
}
