package com.web.repository;

import com.web.domain.Board;
import com.web.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<User, Long> {
    boolean existsByUserNickname(String nickname);
    void deleteByUserNumber(Long userNumber);

}