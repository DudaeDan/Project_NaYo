package com.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.web.domain.User;
import com.web.service.UserService;
import com.web.util.SessionConst;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@GetMapping("/login")
	public String joinForm() {
		return "login/login";
	}

	@GetMapping("findId")
	public String findId() {
		return "login/findId";
	}

	// 아이디 찾기
	@PostMapping("/findId")
	public String findId(@RequestParam String user_name, @RequestParam String user_phone, Model model) {
		User user = userService.findUserByNameAndPhone(user_name, user_phone);
		if (user != null) {
			model.addAttribute("foundId", user.getUserId());
			return "login/foundId"; // 아이디 찾기 성공 시 결과 페이지로 이동
		} else {
			model.addAttribute("findIdError", true);
			return "login/findId"; // 아이디 찾기 폼에 에러 메시지와 함께 다시 표시
		}
	}

	@GetMapping("/findPw")
	public String findPw() {
		return "login/findPw";
	}

	// 비밀번호 찾기
	@PostMapping("/findPw")
	public String findPw(@RequestParam String userName, @RequestParam String userId,
			@RequestParam String userPhonenumber, Model model) {
		User user = userService.findUserByNameIdAndPhone(userName, userId, userPhonenumber);
		if (user != null) {
			// 임시 비밀번호 생성
			String temporaryPw = UUID.randomUUID().toString().substring(0, 8);
			userService.updatePassword(userId, temporaryPw);

			try {
				// 임시 비밀번호 이메일로 발송
				userService.sendTemporaryPassword(userId, temporaryPw);
				model.addAttribute("message", "존재하는 아이디 입니다. 임시 비밀번호가 이메일로 발송되었습니다.");
				return "login/login";

			} catch (MailException e) {
				model.addAttribute("findPwError", "메일 전송 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
				return "login/findPw";
			}
		} else {
			model.addAttribute("findPwError", "해당 정보로 사용자를 찾을 수 없습니다.");
			return "login/findPw"; // 비밀번호 찾기 폼에 에러 메시지와 함께 다시 표시
		}
	}

	// 로그인
	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String userPw, HttpSession session, Model model,
			HttpServletRequest request) {
		User user = userService.validateUser(userId, userPw);
		if (user != null) {
//            session.setAttribute("user", user);
//            return "redirect:/board/ranking";
			session = request.getSession();
			session.setAttribute(SessionConst.LOGIN_MEMBER, user);
			return "redirect:/";
		} else {
			model.addAttribute("loginError", true);
			return "login/login";
		}
	}

	// 로그아웃
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate(); // 세션 무효화
//        return "redirect:/board/ranking"; // 로그아웃 후 로그인 페이지로 리다이렉트
//    }

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/board/ranking"; // 로그아웃 후 로그인 페이지로 리다이렉트
	}

	@GetMapping("/register")
	public String registerForm() {
		return "login/register"; // 회원가입 폼을 보여주는 뷰 이름
	}

	// 회원가입
	@PostMapping("/register")
	public String join(@ModelAttribute User user, Model model) {
		if (userService.isUserIdAvailable(user.getUserId())) {
			userService.saveUser(user);
			model.addAttribute("registerSuccess", "회원가입이 완료되었습니다");
			return "redirect:/login/login"; // 회원가입 성공 후 로그인 페이지로 리다이렉트 나중에 메인 페이지로 변경!!!!!!!!!!!!!!
		} else {
			model.addAttribute("joinError", "아이디가 이미 존재합니다. 고쳐보세요 잘");
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
