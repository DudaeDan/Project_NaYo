package com.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.domain.User;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SessionController {
	
	
		 @GetMapping("/logout")
		    public String logout(HttpSession session) {
		        session.invalidate(); // 세션 무효화
		        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 리다이렉트
		    }

		 
		 
		 
//	    @GetMapping("/current-user")
//	    public String getCurrentUser(HttpSession session, Model model) {
//	        User user = (User) session.getAttribute("user");
//	        model.addAttribute("user", user);
//	        return "user/userInfo"; // 현재 사용자 정보를 보여주는 뷰 이름
//	    }

}
