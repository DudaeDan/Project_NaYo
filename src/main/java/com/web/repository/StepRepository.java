package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByBoard(Board board);
    void deleteByBoard(Board board);
    void deleteByBoard_BoardNumber(Long boardNumber);

}