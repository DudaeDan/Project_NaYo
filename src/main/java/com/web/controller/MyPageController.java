package com.web.controller;

import com.web.domain.Answer;
import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.Inquiry;
import com.web.domain.User;
import com.web.service.MyBoardService;
import com.web.service.AnswerService;
import com.web.service.CommentService;
import com.web.service.InquiryService;
import com.web.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private InquiryService inquiryService;
    
    @Autowired
    private AnswerService answerService;
    

    
    
    @GetMapping("/boardlist")
    public String boardList(Model model, HttpSession session,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "8") int size) {
        if(page<1) {
            page = 1;
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            // PageRequest에 정렬 옵션 추가
            PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "boardDate"));
            Page<Board> boardPage = myBoardService.findPostsByUserNumber(userNumber, pageRequest);

            model.addAttribute("boards", boardPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", boardPage.getTotalPages());
        } else {
            return "redirect:/login/login";
        }
        return "mypage/myboardlist";
    }

    // 작성 댓글 글 목록
    @GetMapping("/comment")
    public String commentList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            List<Comments> comments = commentService.findCommentsByUserNumber(userNumber);
            Map<Board, List<Comments>> commentsByBoard = comments.stream()
                    .collect(Collectors.groupingBy(Comments::getBoard));
            model.addAttribute("commentsByBoard", commentsByBoard);
        } else {
            return "redirect:/login/login";
        }
        return "mypage/comment";
    }




    // 개인정보 수정
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
            myUserService.updateUser(user);
            session.setAttribute("user", user);
        } else {
            return "redirect:/login/login";
        }
        return "redirect:/mypage/profile";
    }

    // 닉네임 중복 확인
    @ResponseBody
    @GetMapping("/check-nickname")
    public boolean checkNickname(@RequestParam String nickname) {
        return myUserService.isNicknameAvailable(nickname);
    }
    
    @PostMapping("/delete")
    public String deleteUser(HttpSession session, @RequestParam String currentPw) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUserPw().equals(currentPw)) {
            myUserService.deleteUser(user.getUserNumber());
            session.invalidate();
        } else {
            return "redirect:/mypage/profile?error=password";
        }
        return "redirect:/board/ranking";
    }



 // 문의 리스트
    @GetMapping("/inquiry_list")
    public String inquiryList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Long userNumber = user.getUserNumber();
            List<Inquiry> inquiries = inquiryService.findInquiriesByUserNumber(userNumber);
            model.addAttribute("inquiries", inquiries);
        } else {
            return "redirect:/login/login";
        }
        return "mypage/inquiry_list";
    }

    // 문의 작성 폼
    @GetMapping("/inquiries")
    public String inquiryForm() {
        return "mypage/inquiry_form";
    }

    // 문의 작성
    @PostMapping("/inquiries")
    public String createInquiry(@RequestParam String inquiryTitle,
                                @RequestParam String inquiryContent,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Inquiry inquiry = new Inquiry();
            inquiry.setInquiryTitle(inquiryTitle);
            inquiry.setInquiryContent(inquiryContent);
            inquiry.setUserNumber(user.getUserNumber());
            inquiryService.createInquiry(inquiry);
        } else {
            return "redirect:/login/login";
        }
        return "redirect:/mypage/inquiry_list";
    }

 // 문의 상세 뷰
    @GetMapping("/inquiry/view/{id}")
    public String viewInquiry(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryService.findInquiryById(id);
        if (inquiry != null) {
            Answer answer = answerService.findAnswerByInquiryNumber(inquiry.getInquiryNumber());
            model.addAttribute("inquiry", inquiry);
            model.addAttribute("answer", answer);
        }
        return "mypage/inquiry_view";
    }
    
    
}
