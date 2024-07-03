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

    

}

//package com.web.repository;
//
//import com.web.domain.User;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//
//    //로그인
//	User findByUserIdAndUserPw(String userId, String userPw);
//
//
//
//	//회원가입 중복 체크
//	boolean existsByUserId(String userId);
//	boolean existsByUserNickname(String userNickname);
//	Optional<User> findByUserId(String userId);
//	
//
//
//}
