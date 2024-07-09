package com.web.controller;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.service.MyBoardService;
import com.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyBoardService myBoardService;
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/boardlist")
    public String boardList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            List<Board> boards = myBoardService.findPostsByUserNumber(userNumber);
            model.addAttribute("boards", boards);
        } else {
            return "redirect:/login/login";
        }
        return "mypage/myboardlist";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            return "redirect:/login/login";
        }
        return "mypage/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String userPw,
                                @RequestParam String userName,
                                @RequestParam String userNickname,
                                @RequestParam String userAddress,
                                @RequestParam String userPhonenumber,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setUserPw(userPw);
            user.setUserName(userName);
            user.setUserNickname(userNickname);
            user.setUserAddress(userAddress);
            user.setUserPhonenumber(userPhonenumber);
            myBoardService.updateUser(user);
            session.setAttribute("user", user);
        } else {
            return "redirect:/login/login";
        }
        return "redirect:/mypage/profile";
    }

//    @PostMapping("/delete")
//    public String deleteUser(HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            myBoardService.deleteUser(user.getUserNumber());
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

    @GetMapping("/inquiry")
    public String inquiry() {
        return "mypage/inquiry";
    }
}
