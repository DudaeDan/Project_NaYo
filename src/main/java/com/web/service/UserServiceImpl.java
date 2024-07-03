package com.web.service;

import com.web.domain.User;
import com.web.repository.UserRepository;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isUserIdAvailable(String userId) {
        return userRepository.findByUserId(userId) == null;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
    
    @Override
    public User validateUser(String userId, String userPw) {
        User user = userRepository.findByUserId(userId);
        if (user != null && user.getUserPw().equals(userPw)) {
            return user;
        }
        return null;
    }
    @Override
    public User findUserByNameAndPhone(String user_name, String user_phone) {
        return userRepository.findByUserNameAndUserPhonenumber(user_name, user_phone);
    }
    
//    if(username==email==전화번호) {
//    	이메일로 랜덤 설정된 새로운 비밀번호를 보내주며
//    	데이터베이스에 해당 유저의 비밀번호를 업데이트한다.
//    	result 값을 1을 보내준다
//    }else {
//    	result 값을 0을 보내준다
//    }
}
