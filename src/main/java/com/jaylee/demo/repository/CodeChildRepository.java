package com.jaylee.demo.repository;

import com.jaylee.demo.model.CodeChild;
import com.jaylee.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeChildRepository extends JpaRepository<CodeChild, Long> {
    List<CodeChild> findByCodenameOrderBySeqAsc(String code_name);
}
