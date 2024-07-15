package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByBoardOrderByLikesDesc(Board board);
    List<Comments> findByBoard(Board board);
	void deleteByUser_UserNumber(Long userNumber);

    List<Comments> findByUser_UserNumber(Long userNumber);
    List<Comments> findByBoard_BoardNumber(Long boardNumber);
    
    @Query("SELECT c FROM Comments c WHERE c.user.userNumber = :userNumber ORDER BY c.board.boardNumber DESC")
    List<Comments> findByUserNumberOrderByboardNumberDesc(@Param("userNumber") Long userNumber);

    
    
}