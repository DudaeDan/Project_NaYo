package com.web.controller;

import com.web.domain.Board;

import com.web.domain.User;

import com.web.service.MyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyBoardService myBoardService;
   

    @GetMapping("/boardlist")
    public String boardList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user"); // 세션에서 user를 가져옴
        if (user != null) {
            Long userNumber = user.getUserNumber(); // user 객체에서 userNumber를 가져옴
            List<Board> boards = myBoardService.findPostsByUserNumber(userNumber);
            model.addAttribute("boards", boards);
        } else {
            return "redirect:/login/login"; // 로그인된 사용자가 없으면 로그인 페이지로 리다이렉트
        }
        return "mypage/myboardlist";
    }

    
    
    @GetMapping("/profile")
    public String profile() {
        return "mypage/profile";
    }

    @GetMapping("/inquiry")
    public String inquiry() {
        return "mypage/inquiry";
    }
}
