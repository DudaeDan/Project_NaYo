package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mypage")
public class MypageController {

	@PostMapping("mypage")
	public String mypage() {
		return "mypage/memberInfo";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
