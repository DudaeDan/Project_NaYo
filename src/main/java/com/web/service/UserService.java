package com.web.service;

import com.web.domain.User;

public interface UserService {
	
    boolean isUserIdAvailable(String userId);
    void saveUser(User user);
	User validateUser(String userId, String userPw);
	User findUserByNameAndPhone(String user_name, String user_phone);
	User findUserByNameIdAndPhone(String userName, String userId, String userPhonenumber);
	String generateTemporaryPassword();
	void sendTemporaryPassword(String userId, String temporaryPw);
	void updatePassword(String userId, String newPassword);
	boolean isNicknameAvailable(String nickname);
	

}
