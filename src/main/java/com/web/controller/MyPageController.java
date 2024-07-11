package com.web.controller;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.service.MyBoardService;
import com.web.service.CommentService;
import com.web.service.MyUserService;
import com.web.util.SessionConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyBoardService myBoardService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private HttpSession session;
    
//    @GetMapping("/boardlist")
//    public String boardList(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user"); // 세션에서 user를 가져옴
//        if (user != null) {
//            Long userNumber = user.getUserNumber(); // user 객체에서 userNumber를 가져옴
//            List<Board> boards = myBoardService.findPostsByUserNumber(userNumber);
//            model.addAttribute("boards", boards);
//        } else {
//            return "redirect:/login/login"; // 로그인된 사용자가 없으면 로그인 페이지로 리다이렉트
//        }
//        return "mypage/myboardlist";
//    }
    @GetMapping("/boardlist")
    public String boardList(Model model) {
    	Object user = session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (user != null) {
    		Long userNumber = ((User) user).getUserNumber(); // user 객체에서 userNumber를 가져옴
    		List<Board> boards = myBoardService.findPostsByUserNumber(userNumber);
    		model.addAttribute("boards", boards);
    	} else {
    		return "redirect:/login/login"; // 로그인된 사용자가 없으면 로그인 페이지로 리다이렉트
    	}
    	return "mypage/myboardlist";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
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
                                @RequestParam String userPhonenumber
                                ) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user != null) {
            user.setUserPw(userPw);
            user.setUserName(userName);
            user.setUserNickname(userNickname);
            user.setUserAddress(userAddress);
            user.setUserPhonenumber(userPhonenumber);
            myUserService.updateUser(user);
            session.setAttribute("user", user);
        } else {
            return "redirect:/login/login";
        }
        return "redirect:/mypage/profile";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String currentPw) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user != null && user.getUserPw().equals(currentPw)) {
            myUserService.deleteUser(user.getUserNumber());
            session.invalidate();
        } else {
            return "redirect:/mypage/profile?error=password";
        }
        return "redirect:/board/ranking";
    }

    @GetMapping("/inquiry")
    public String inquiry() {
        return "mypage/inquiry";
    }

    @ResponseBody
    @GetMapping("/check-nickname")
    public String checkNickname(@RequestParam String nickname) {
        boolean isAvailable = myUserService.isNicknameAvailable(nickname);
        return String.valueOf(isAvailable);
    }
}