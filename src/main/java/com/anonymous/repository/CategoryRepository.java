package com.anonymous.repository;

import com.anonymous.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findOneByCode(String code);
}
