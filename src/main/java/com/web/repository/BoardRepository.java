package com.web.repository;

import com.web.domain.Board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Board findByBoardNumber(Long boardNumber);
    List<Board> findByUser_UserNumber(Long userNumber);

    List<Board> findTop20ByOrderByBoardLikeDesc();
    List<Board> findTop20ByOrderByBoardDateDesc();
    List<Board> findTop20ByBoardDateAfterOrderByBoardHitDesc(LocalDateTime date);
    List<Board> findByBoardTitleContaining(String title);
    List<Board> findByBoardContentContaining(String content);
    List<Board> findByBoardTitleContainingOrderByBoardLikeDesc(String title);
    List<Board> findByBoardContentContainingOrderByBoardLikeDesc(String content);

    @Query("SELECT b FROM Board b WHERE b.boardLike >= 5")
    Page<Board> findBoardsAtLeast5Likes(Pageable pageable);
 
 
}

