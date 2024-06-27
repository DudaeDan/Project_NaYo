package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/layout")
    public String layout() {
        return "layout";
    }
    
    // 테스트
    @GetMapping("/admin")
    public String admin() {
        return "admin/admin_main";
    }
    

}
