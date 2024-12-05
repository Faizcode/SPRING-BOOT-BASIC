package com.testproject.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testproject.testproject.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    
}
