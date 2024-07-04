package com.web.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.web.domain.Board;
import com.web.domain.Inquiry;
import com.web.domain.Notice;
import com.web.domain.User;
import com.web.service.AdminService;

@SessionAttributes("user")
@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminService adminService;


	// test
	@GetMapping()
	public String adminLoginForm() {
		return "admin/admin_login";
	}

	@PostMapping("/login")
	public String adminLogin(User user, Model model) {
		User findUser = adminService.getLoginUser(user);

		if (findUser != null && findUser.getUserPw().equals(user.getUserPw())) {
			model.addAttribute("user", findUser);
			return "forward:main";
		} else {
			return "redirect:index";
		}

	}
//	@PostMapping("/login")
//	public String adminLogin(User user, Model model) {
//		User findUser = adminService.getLoginUser(user);
//		if (findUser != null && findUser.getUserPw().equals(user.getUserPw())) {
//			if (findUser.getUserRole() == 1) {
//				model.addAttribute("member", findUser);
//				return "forward:main";
//			} else {
//				return "redirect:/";
//			}
//		}
//		return "redirect:/admin/admin_login";
//	}

	// ---- 메인 ----

	// 메인페이지 데이터
	@GetMapping("main")
	public String admin(Model model) {
		model.addAttribute("mainMember", adminService.getMainUserList());
		model.addAttribute("mainBoard", adminService.getMainBoardList());
		model.addAttribute("mainNotice", adminService.getMainNoticeList());
		model.addAttribute("mainInquiry", adminService.getMainInquiryList());
		return "admin/admin_main";
	}

	// ---- 멤버 ----

	// 멤버 리스트
	@GetMapping("memberList")
	public String adminMemberList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
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
	public String viewMember(@PathVariable("id") Long id, Model model) {
		model.addAttribute("memberDetail", adminService.getMember(id));
		return "admin/admin_member_view";
	}

	// ---- 게시판 ----

	// 게시판 리스트
	@GetMapping("boardList")
	public String adminBoardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Board> boardPage = adminService.getBoardList(pageable, searchType, searchKeyword);
		model.addAttribute("boardList", boardPage.getContent());
		model.addAttribute("totalPages", boardPage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		return "admin/admin_board";
	}

	// 게시글 상세보기
	@GetMapping("boardView/{id}")
	public String viewBoard(@PathVariable("id") Long id, Model model) {
		model.addAttribute("boardDetail", adminService.getBoard(id));
		return "admin/admin_board_view";
	}

	// 게시글 수정 페이지
	@GetMapping("boardModifyForm")
	public String boardModify(@RequestParam("boardNumber") Long boardNumber, Model model) {
		Board board = adminService.getBoard(boardNumber);
		model.addAttribute("boardModify", board);
		return "admin/admin_board_modify";
	}

	// 게시글 수정
	@PostMapping("boardModify")
	public String modifyBoard(@RequestParam("boardNumber") Long boardNumber,
			@RequestParam("boardTitle") String boardTitle, @RequestParam("boardContent") String boardContent,
			Model model) {
		adminService.modifyBoard(boardNumber, boardTitle, boardContent);
		return "redirect:/admin/boardList";
	}

	// 게시글 삭제
	@PostMapping("boardDelete")
	public String boardDelete(@RequestParam("boardNumber") Long boardNumber) {
		adminService.deleteBoard(boardNumber);
		return "redirect:/admin/boardList";
	}

	// ---- 공지 ----

	// 공지 리스트
	@GetMapping("noticeList")
	public String adminNoticeList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Notice> noticePage = adminService.getNoticeList(pageable, searchType, searchKeyword);
		model.addAttribute("noticeList", noticePage.getContent());
		model.addAttribute("totalPages", noticePage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		return "admin/admin_notice";
	}

	// 공지 자세히보기
	@GetMapping("noticeView/{id}")
	public String viewNotice(@PathVariable("id") Long id, Model model) {
		model.addAttribute("noticeDetail", adminService.getNotice(id));
		return "admin/admin_notice_view";
	}

	// 공지 작성 페이지
	@GetMapping("noticeWriteForm")
	public String writeNotice() {
		return "admin/admin_notice_write";
	}

	// 공지 작성
	@PostMapping("noticeWrite")
	public String WriteNotice(@RequestParam("userNumber") Long userNumber,
			@RequestParam("noticeTitle") String noticeTitle, @RequestParam("noticeContent") String noticeContent) {
		adminService.writeNotice(userNumber, noticeTitle, noticeContent);
		return "redirect:/admin/noticeList";
	}

	// 공지 수정 페이지
	@GetMapping("noticeModifyForm")
	public String noticeModify(@RequestParam("noticeNumber") Long noticeNumber, Model model) {
		Notice notice = adminService.getNotice(noticeNumber);
		model.addAttribute("noticeModify", notice);
		return "admin/admin_notice_modify";
	}

	// 공지 수정
	@PostMapping("noticeModify")
	public String modifyNotice(@RequestParam("noticeNumber") Long noticeNumber,
			@RequestParam("noticeTitle") String noticeTitle, @RequestParam("noticeContent") String noticeContent) {
		adminService.modifyNotice(noticeNumber, noticeTitle, noticeContent);
		return "redirect:/admin/noticeList";
	}

	// 공지 삭제
	@PostMapping("noticeDelete")
	public String noticeDelete(@RequestParam("noticeNumber") Long noticeNumber) {
		adminService.deleteNotice(noticeNumber);
		return "redirect:/admin/noticeList";
	}

	// ---- 문의 ----

	// 문의글 리스트
	@GetMapping("inquiryList")
	public String adminInquiryList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
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
	public String viewInquiry(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inquiryDetail", adminService.getInquiry(id));
		return "admin/admin_inquiry_view";
	}

	// 문의글 답변
	@PostMapping("inquiryAnswer")
	public String answerInquiry() {

		return "redirect:/admin/inquiryList";
	}

}
