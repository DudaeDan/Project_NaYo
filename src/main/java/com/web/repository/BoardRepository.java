package com.web.repository;

import com.web.domain.Board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Board findByBoardNumber(Long boardNumber);
    List<Board> findByUser_UserNumber(Long userNumber);

    List<Board> findTop20ByOrderByBoardLikeDesc();
    List<Board> findTop20ByOrderByBoardDateDesc();
    List<Board> findTop20ByBoardDateAfterOrderByBoardHitDesc(LocalDateTime date);

    
}

