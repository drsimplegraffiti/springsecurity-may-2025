package com.drsimple.jwtsecurity.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // loads login.html from templates folder
    }
}