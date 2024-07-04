package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByBoardBoardNumber(Long boardNumber);
    Like findByUserAndBoard(User user, Board board);

}
