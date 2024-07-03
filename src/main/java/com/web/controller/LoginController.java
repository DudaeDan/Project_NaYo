package com.web.controller;

import com.web.domain.User;
import com.web.service.UserService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String joinForm() {
        return "login/login";
    }
    
    @GetMapping("findId")
    public String findId() {
        return "login/findId";
    }
    
    
    @PostMapping("/findId")
    public String findId(@RequestParam String user_name, @RequestParam String user_phone, Model model) {
        User user = userService.findUserByNameAndPhone(user_name, user_phone);
        if (user != null) {
            model.addAttribute("foundId", user.getUserId());
            return "login/foundId"; // 아이디 찾기 성공 시 결과 페이지로 이동
        } else {
            model.addAttribute("findIdError", "해당 정보로 아이디를 찾을 수 없습니다.");
            return "login/foundId"; // 아이디 찾기 폼에 에러 메시지와 함께 다시 표시
        }
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String userPw, HttpSession session, Model model) {
        User user = userService.validateUser(userId, userPw);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/layout"; // 로그인 성공 후 메인 페이지로 리다이렉트 나중에 메인 페이지로 변경!!!!!!!!!!!!!!
        } else {
            model.addAttribute("loginError", true); // 로그인 실패 플래그 설정
            return "login/login"; // 로그인 폼에 에러 메시지와 함께 다시 표시
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/layout"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }
    
    @GetMapping("/register")
    public String registerForm() {
        return "login/register"; // 회원가입 폼을 보여주는 뷰 이름
    }

    @PostMapping("/register")
    public String join(@ModelAttribute User user, Model model) {
        if (userService.isUserIdAvailable(user.getUserId())) {
            userService.saveUser(user);
            return "redirect:/login/login"; // 회원가입 성공 후 로그인 페이지로 리다이렉트 나중에 메인 페이지로 변경!!!!!!!!!!!!!!
        } else {
            model.addAttribute("joinError", "아이디가 이미 존재합니다.");
            return "login/register"; // 회원가입 폼에 에러 메시지와 함께 다시 표시
        }
    }

    @ResponseBody
    @GetMapping("/check-id")
    public String checkId(@RequestParam String userId) {
        boolean isAvailable = userService.isUserIdAvailable(userId);
        return isAvailable ? "사용 가능한 아이디입니다." : "아이디가 이미 존재합니다.";
    }
}
