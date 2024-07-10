package com.web.service;

import com.web.domain.User;

public interface MyUserService {
    void updateUser(User user);
    void deleteUser(Long userNumber);
    boolean isNicknameAvailable(String nickname);
}