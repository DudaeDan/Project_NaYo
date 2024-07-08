package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByBoardOrderByLikesDesc(Board board);
    List<Comments> findByBoard(Board board);
}