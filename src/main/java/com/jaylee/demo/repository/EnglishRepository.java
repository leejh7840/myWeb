package com.jaylee.demo.repository;

import com.jaylee.demo.model.English;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnglishRepository extends JpaRepository<English, Long> {
    Page<English> findByEnglishtextContainingOrderByIdDesc(String english, Pageable pageable);
}
