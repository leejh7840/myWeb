package com.jaylee.demo.repository;

import com.jaylee.demo.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    List<Board> findByTitleAndContent(String title, String content);
    @Query(value = "SELECT * FROM BOARD WHERE TITLE = ?1 OR CONTENT LIKE %?2%", nativeQuery = true)
    List<Board> findByTitleOrContentLike(String title, String content);
    List<Board> findByContentLikeOrTitle(String content, String title);
    Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content, Pageable pageable);
    List<Board> findByCategory_id(Long category_id);
}
