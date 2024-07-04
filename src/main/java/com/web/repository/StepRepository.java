package com.web.repository;

import com.web.domain.Step;
import com.web.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByBoard(Board board);
}