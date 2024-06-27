package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
	// INDEX
    @GetMapping("/layout")
    public String layout() {
        return "layout";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
}
