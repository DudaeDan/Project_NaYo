package com.web.service;

import com.web.domain.User;
import com.web.repository.UserRepository;


import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;

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
    
    @Override
    public User findUserByNameIdAndPhone(String userName, String userId, String userPhonenumber) {
        return userRepository.findByUserNameAndUserIdAndUserPhonenumber(userName, userId, userPhonenumber);
    }
    
    @Override
    public String generateTemporaryPassword() {
    	 return UUID.randomUUID().toString().substring(0, 8);
    }
    
    @Override
    public void sendTemporaryPassword(String userId, String temporaryPw) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(userId);
                helper.setSubject("임시 비밀번호 안내");
                helper.setText(buildHtmlEmailContent(user.getUserName(), temporaryPw), true);
                //helper.setFrom("dksekgp111@naver.com"); // spring.mail.username 값과 동일하게 설정
                helper.setFrom("ahndaaa1230@gamil.com"); // spring.mail.username 값과 동일하게 설정
                javaMailSender.send(message);
            } catch (MailException | MessagingException e) {
            	e.printStackTrace();
                throw new RuntimeException("메일 전송 중 오류가 발생했습니다.", e);
            }
        }
    }

    private String buildHtmlEmailContent(String userName, String temporaryPw) {
        return "<html>" +
                "<body>" +
                "<h3>안녕하세요, " + userName + "님</h3>" +
                "<p>비밀번호 찾기를 요청하셔서 임시 비밀번호를 발급해드립니다.</p>" +
                "<p><strong>임시 비밀번호: " + temporaryPw + "</strong></p>" +
                "<p>로그인 후 반드시 비밀번호를 변경해주시기 바랍니다.</p>" +
                "<p>감사합니다.<br>레시피 웹사이트 팀</p>" +
                "</body>" +
                "</html>";
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
    	
    	User user = userRepository.findByUserId(userId);
        if (user != null) {
        	user.setUserPw(newPassword);
            userRepository.save(user);
            System.out.println("Sending email to: " + userId);
            System.out.println("Temporary password: " + newPassword);
        }
    	
    }
}
    
//    if(username==email==전화번호) {
//    	이메일로 랜덤 설정된 새로운 비밀번호를 보내주며
//    	데이터베이스에 해당 유저의 비밀번호를 업데이트한다.
//    	result 값을 1을 보내준다
//    }else {
//    	result 값을 0을 보내준다
//    }

