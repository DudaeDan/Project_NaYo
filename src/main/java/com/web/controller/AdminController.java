package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

	// 테스트
	
    @GetMapping("main")
    public String admin() {
        return "admin/admin_main";
    }
    
    @GetMapping("memberList")
    public String adminMemberList() {
        return "admin/admin_member";
    }
    
    @GetMapping("boardList")
    public String adminBoardList() {
    	return "admin/admin_board";
    }
    
    @GetMapping("noticeList")
    public String adminNoticeList() {
    	return "admin/admin_notice";
    }
    
    @GetMapping("inquiryList")
    public String adminInquiryList() {
    	return "admin/admin_inquiry";
    }
	
}
