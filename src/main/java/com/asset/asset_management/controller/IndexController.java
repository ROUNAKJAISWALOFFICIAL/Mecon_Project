package com.asset.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String redirectTohHome() {
        return "redirect:/home.html";
    }
    @GetMapping("/index")
    public String redirectToLogin() {
        return "redirect:/index.html";
    }

    @GetMapping("/admin")
    public String redirectToAdmin() {
        return "redirect:/admin.html";
    }

    @GetMapping("/employee")
    public String redirectToEmployee() {
        return "redirect:/employee.html";
    }
}
