package com.web.repository;

import com.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<User, Long> {
    boolean existsByUserNickname(String nickname);
    void deleteByUserNumber(Long userNumber);
}