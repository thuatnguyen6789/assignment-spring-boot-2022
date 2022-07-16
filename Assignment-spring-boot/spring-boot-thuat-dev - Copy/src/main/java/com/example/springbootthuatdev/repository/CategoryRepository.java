package com.example.springbootthuatdev.repository;

import com.example.springbootthuatdev.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
