package com.hafhaf.eshop.controller;

import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    // Kontroler
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        boolean success = userService.register(username, email, password);

        if (success) {
            User user = userService.login(username, password);
            userService.setLoggedInUser(session, user);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    // Login
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userService.login(username, password);

        if (user != null) {
            userService.setLoggedInUser(session, user);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    // Logout
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        userService.logout(session);
        return "redirect:/";
    }
}