package com.anonymous.repository;

import com.anonymous.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INewsRepository extends JpaRepository<NewsEntity, Long> {
    NewsEntity findOneById(Long id);
}
