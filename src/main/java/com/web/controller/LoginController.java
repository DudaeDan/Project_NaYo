package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
	// INDEX
    @GetMapping("/layout")
    public String layout() {
        return "/login/logintest";
    }
    @GetMapping("/join")
    public String layasdut() {
        return "/login/join";
    }
    @GetMapping("/123")
    public String layasd123ut() {
        return "/login/findPw";
    }
    @GetMapping("/1234")
    public String layouthaha() {
    	return "/login/findId";
    }
    
}
