package com.user.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(value = "/users/login")
    public String showLoginPage() {
        return "login";
    }
}