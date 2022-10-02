package com.hodob.marketPractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInOutController {

    @GetMapping("/signin")
    public String loginForm() {
        return "signin";
    }

    @GetMapping("/logout")
    public String logoutForm() {
        return "logout";
    }
}
