package com.anonymous.repository;

import com.anonymous.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    NewsEntity findOneById(Long id);
}
