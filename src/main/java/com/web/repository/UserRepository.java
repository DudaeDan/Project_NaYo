package com.web.repository;

import com.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// SQL을 편하게 작성해주는 인터페이스
// 콘솔에 Hibernate를 이용해서 확인 가능하다.



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByUserId(String userId);
    // 유저 아이디를 찾는다.
    User findByUserNameAndUserPhonenumber(String userName, String userPhonenumber);
	User findByUserNameAndUserIdAndUserPhonenumber(String userName, String userId, String userPhonenumber);

    

}

