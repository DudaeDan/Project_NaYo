package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByBoardBoardNumber(Long boardNumber);
    Like findByUserAndBoard(User user, Board board);

	void deleteByBoard_BoardNumber(Long boardNumber);
	void deleteByUser_UserNumber(Long userNumber);
	

}