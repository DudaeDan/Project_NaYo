package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.domain.User;
import com.web.service.MemberService;


//@SessionAttributes("member")
@Controller
@RequestMapping("login")
public class LoginController{
//    
//	@Autowired
//	private MemberService ms;
//	
//    @GetMapping("/login")
//    public void loginView() {
//       
//    }
//    
//    @PostMapping("/login")
//    public String login(User user, Model model) {
//    	User findUser = MemberService.getUser(user);
//    	
//    	if(findUser != null && findUser.getUserPw().equals(user.getUserPw())) {
//    		model.addAttribute("user", findUser);
//    		return "forward:boardlist";
//    	}
//    	 return "redirect:login";
//    }
    
    @GetMapping("login")
    public String login() {
    	return "login/login";
    }
    
    @GetMapping("findPw")
    public String findPw() {
    	return "login/findPw";
    }
    @GetMapping("findId")
    public String findId() {
    	return "login/findId";
    }
    @GetMapping("join")
    public String join() {
    	return "login/join";
    }
    
    
    
    
    
    
}
