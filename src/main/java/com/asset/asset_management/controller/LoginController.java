package com.asset.asset_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asset.asset_management.entities.User;
import com.asset.asset_management.interfaces.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User u = userRepository.findByUsername(username);

        if (u != null && u.getPassword().equals(password)) {
            if ("ADMIN".equalsIgnoreCase(u.getRole())) {
                return "redirect:/admin.html";
            } else if ("EMPLOYEE".equalsIgnoreCase(u.getRole())) {
                return "redirect:/employee.html";
            }
        }

        return "redirect:/index.html?error=true";
    }
}
