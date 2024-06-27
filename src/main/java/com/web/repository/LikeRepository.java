package com.web.repository;

import com.web.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByBoardBoardNumber(Long boardNumber);
}
