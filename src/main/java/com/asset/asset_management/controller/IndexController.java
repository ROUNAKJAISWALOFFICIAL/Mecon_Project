package com.asset.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";  // Open login page
    }

    @GetMapping("/admin")
    public String home() {
        return "redirect:/home.html";   // After login, redirect to static page
    }
}
