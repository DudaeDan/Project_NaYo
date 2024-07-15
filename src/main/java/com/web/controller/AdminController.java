package com.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.web.domain.Answer;
import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.Inquiry;
import com.web.domain.Notice;
import com.web.domain.Reply;
import com.web.domain.TmpBoard;
import com.web.domain.User;
import com.web.service.AdminService;
import com.web.service.BoardService;
import com.web.service.CommentService;
import com.web.service.ReplyService;
import com.web.util.SessionConst;

@Controller
@SessionAttributes("user")
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpSession session;
    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private ReplyService replyService;
    
    // 로그인 페이지
    @GetMapping("/login")
    public String adminLoginForm() {
        return "admin/admin_login";
    }


    // 로그인
    @PostMapping("login")
    public String login(@RequestParam String userId, @RequestParam String userPw, Model model, HttpServletRequest request) {
        User user = adminService.adminLogin(userId, userPw);
        System.out.println(userId);
        System.out.println(userPw);
        if (user == null) {
            model.addAttribute("loginError", true); // 로그인 에러 메시지 설정
            return "admin/admin_login"; // 로그인 페이지로 이동
        } else {
            if (user.getUserRole() == 1) {
                session = request.getSession();
                session.setAttribute(SessionConst.LOGIN_MEMBER, user);
                return "redirect:/admin/main";
            } else {
                model.addAttribute("adminOnlyError", true); // 관리자 전용 에러 메시지 설정
                return "admin/admin_login"; // 로그인 페이지로 이동
            }
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/admin/login";
    }


    // 메인페이지 데이터
    @GetMapping("/main")
    public String adminMain(Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("mainMember", adminService.getMainUserList());
        model.addAttribute("mainBoard", adminService.getMainBoardList());
        model.addAttribute("mainNotice", adminService.getMainNoticeList());
        model.addAttribute("mainInquiry", adminService.getMainInquiryList());
        return "admin/admin_main";
    }

    private boolean isAdminLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        User loginMember = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return loginMember != null && loginMember.getUserRole() == 1;
    }

    // 멤버 리스트
    @GetMapping("memberList")
    public String adminMemberList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  @RequestParam(value = "searchType", required = false) String searchType,
                                  @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                  HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> memberPage = adminService.getMemberList(pageable, searchType, searchKeyword);
        model.addAttribute("memberList", memberPage.getContent());
        model.addAttribute("totalPages", memberPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);
        return "admin/admin_member";
    }

    // 멤버 상세보기
    @GetMapping("memberView/{id}")
    public String viewMember(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        model.addAttribute("memberDetail", adminService.getMember(id));
        return "admin/admin_member_view";
    }

    // 회원탈퇴
    @PostMapping("/deleteMember/{id}")
    @ResponseBody
    public String deleteMember(@PathVariable Long id) {
        try {
            adminService.deleteMember(id);
            return "deleted";
        } catch (Exception e) {
            return "error";
        }
    }
    // 게시판 리스트
    @GetMapping("boardList")
    public String adminBoardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value = "searchType", required = false) String searchType,
                                 @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                 HttpServletRequest request) {
        User loginMember = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null || loginMember.getUserRole() != 1) {
            model.addAttribute("adminOnlyError", true); // 관리자 전용 에러 메시지 설정
            return "admin/admin_login"; // 로그인 페이지로 이동
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = adminService.getBoardList(pageable, searchType, searchKeyword);
        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);
        return "admin/admin_board";
    }

    @GetMapping("/boardView/{id}")
    public String viewAdminBoard(@PathVariable Long id, Model model, HttpServletRequest request ) {
        // 관리자 로그인 여부 확인
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login"; // 관리자 로그인 페이지로 리디렉션
        }

        Board board = boardService.findBoardById(id);
        if (board != null) {
            model.addAttribute("board", board);
            model.addAttribute("steps", board.getSteps());
            model.addAttribute("ingredients", board.getIngredients());

            // 댓글 목록 추가
            List<Comments> comments = commentService.findCommentsByBoard(board);
            model.addAttribute("comments", comments);

            // 베스트 댓글 계산
            int maxLikes = comments.stream().mapToInt(Comments::getLikes).max().orElse(0);
            if (maxLikes > 0) {
                List<Comments> bestComments = comments.stream()
                        .filter(comment -> comment.getLikes() == maxLikes)
                        .collect(Collectors.toList());
                model.addAttribute("bestComments", bestComments);
            } else {
                model.addAttribute("bestComments", null);
            }
        }
        return "/admin/admin_board_view";
    }
    	
    @PostMapping("/deleteComment/{id}")
    @ResponseBody
    public String deleteComment(@PathVariable Long id) {
        try {
            adminService.deleteComment(id);
            return "deleted";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/deleteReply/{id}")
    @ResponseBody
    public String deleteReply(@PathVariable Long id) {
        try {
            adminService.deleteReply(id);
            return "deleted";
        } catch (Exception e) {
            return "error";
        }
    }
	    
    // 게시글 수정 페이지
    @GetMapping("boardModifyForm")
    public String boardModify(@RequestParam("boardNumber") Long boardNumber, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Board board = adminService.getBoard(boardNumber);
        model.addAttribute("boardModify", board);
        return "admin/admin_board_modify";
    }

    // 게시글 수정
    @PostMapping("boardModify")
    public String modifyBoard(@ModelAttribute Board tmpBoard, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        adminService.modifyBoard(tmpBoard);
        return "redirect:/admin/boardList";
    }

    // 게시글 삭제
    @PostMapping("boardDelete")
    @ResponseBody
    public String boardDelete(@RequestParam("boardNumber") Long boardNumber, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        try {
            adminService.deleteBoardWithFiles(boardNumber);
            return "redirect:/admin/boardList";        
        	} catch (Exception e) {
            // 로그에 오류 메시지 출력
            System.err.println("Error deleting board: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    // 공지 리스트
    @GetMapping("noticeList")
    public String adminNoticeList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  @RequestParam(value = "searchType", required = false) String searchType,
                                  @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                  HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Notice> noticePage = adminService.getNoticeList(pageable, searchType, searchKeyword);
        model.addAttribute("noticeList", noticePage.getContent());
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);
        return "admin/admin_notice";
    }

    // 공지 상세보기
    @GetMapping("noticeView/{id}")
    public String viewNotice(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        model.addAttribute("noticeDetail", adminService.getNotice(id));
        return "admin/admin_notice_view";
    }

    // 공지 작성 페이지
    @GetMapping("noticeWriteForm")
    public String writeNotice(HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        return "admin/admin_notice_write";
    }

    // 공지 작성
    @PostMapping("noticeWrite")
    public String writeNotice(@RequestParam("userNumber") String userNumberString,
                              @RequestParam("noticeTitle") String noticeTitle,
                              @RequestParam("noticeContent") String noticeContent,
                              HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Long userNumber = Long.parseLong(userNumberString);
        adminService.writeNotice(userNumber, noticeTitle, noticeContent);
        return "redirect:/admin/noticeList";
    }

    // 공지 수정 페이지
    @GetMapping("noticeModifyForm")
    public String noticeModify(@RequestParam("noticeNumber") Long noticeNumber, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Notice notice = adminService.getNotice(noticeNumber);
        model.addAttribute("noticeModify", notice);
        return "admin/admin_notice_modify";
    }

    // 공지 수정
    @PostMapping("noticeModify")
    public String modifyNotice(@RequestParam("noticeNumber") Long noticeNumber,
                               @RequestParam("noticeTitle") String noticeTitle,
                               @RequestParam("noticeContent") String noticeContent,
                               HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        adminService.modifyNotice(noticeNumber, noticeTitle, noticeContent);
        return "redirect:/admin/noticeList";
    }

    // 공지 삭제
    @PostMapping("noticeDelete")
    public String noticeDelete(@RequestParam("noticeNumber") Long noticeNumber, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        adminService.deleteNotice(noticeNumber);
        return "redirect:/admin/noticeList";
    }

    // 문의글 리스트
    @GetMapping("inquiryList")
    public String adminInquiryList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                   @RequestParam(value = "searchType", required = false) String searchType,
                                   @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                   HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Inquiry> inquiryPage = adminService.getInquiryList(pageable, searchType, searchKeyword);
        model.addAttribute("inquiryList", inquiryPage.getContent());
        model.addAttribute("totalPages", inquiryPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);
        return "admin/admin_inquiry";
    }

    // 문의글 상세보기
    @GetMapping("inquiryView/{id}")
    public String viewInquiry(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        model.addAttribute("inquiryDetail", adminService.getInquiry(id));
        Answer answer = adminService.getAnswer(id);
        if (answer != null) {
            model.addAttribute("answer", answer);
        }
        return "admin/admin_inquiry_view";
    }

    // 문의 답변/수정 페이지
    @GetMapping("inquiryAnswerForm")
    public String inquiryAnswerForm(@RequestParam("inquiryNumber") Long inquiryNumber, Model model, HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        model.addAttribute("inquiryDetail", adminService.getInquiry(inquiryNumber));
        Answer answer = adminService.getAnswer(inquiryNumber);
        if (answer != null) {
            model.addAttribute("answer", answer);
        }
        return "admin/admin_inquiry_answer";
    }

    // 문의글 답변/수정
    @PostMapping("inquiryAnswer")
    public String answerInquiry(@RequestParam("inquiryNumber") String inquiryNumberString,
                                @RequestParam("userNumber") String userNumberString,
                                @RequestParam("answerContent") String answerContent,
                                HttpServletRequest request) {
        if (!isAdminLoggedIn(request)) {
            return "redirect:/admin/admin_login";
        }
        Long inquiryNumber = Long.parseLong(inquiryNumberString);
        Long userNumber = Long.parseLong(userNumberString);
        adminService.addInquiryAnswer(inquiryNumber, userNumber, answerContent);
        adminService.AnswerDone(inquiryNumber);
        return "redirect:/admin/inquiryList";
    }
    
    // 세션 적용
    private boolean isUserLoggedIn() {
        User loginUser = (User) session.getAttribute("user");
        return loginUser != null;
    }
}