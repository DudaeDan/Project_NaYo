package com.web.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.User;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {

	User findByUserIdAndUserPw(String userId, String userPw);

	List<User> findTop6ByOrderByUserNumberDesc();

	Page<User> findAllByOrderByUserNumberDesc(Pageable pageable);

	Page<User> findByUserNameContainingOrderByUserNumberDesc(String userName, Pageable pageable);

	Page<User> findByUserIdContainingOrderByUserNumberDesc(String userId, Pageable pageable);

}
