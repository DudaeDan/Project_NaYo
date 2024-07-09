package com.web.repository;

import com.web.domain.Board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Board findByBoardNumber(Long boardNumber);
    List<Board> findByUser_UserNumber(Long userNumber);


}

