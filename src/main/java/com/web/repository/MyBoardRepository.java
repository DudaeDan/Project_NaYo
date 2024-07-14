package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MyBoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUser_UserNumber(Long userNumber);
    void deleteByUser_UserNumber(Long userNumber);
    @Query("SELECT DISTINCT b FROM Board b JOIN b.comments c WHERE c.user.userNumber = :userNumber")
    List<Board> findBoardsByUserComments(@Param("userNumber") Long userNumber);
    Page<Board> findByUser_UserNumber(Long userNumber, Pageable pageable);

}