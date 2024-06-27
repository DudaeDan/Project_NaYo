package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	   // 테스트
    @GetMapping("/admin")
    public String admin() {
        return "admin/admin_main";
    }
    
	
}
