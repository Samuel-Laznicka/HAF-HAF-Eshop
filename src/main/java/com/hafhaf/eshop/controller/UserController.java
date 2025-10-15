package com.hafhaf.eshop.controller;

import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.repository.UserRepository;
import com.hafhaf.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // Kontroler
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Přidání bodů
    @PostMapping("/user/add-money")
    public String addMoney(
            @RequestParam BigDecimal amount,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/";
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "redirect:/user";
        }

        userService.addPoints(user, amount);
        userService.setLoggedInUser(session, user);

        return "redirect:/user";
    }

    // Úprava uživatele
    @PostMapping("/user/edit")
    public String editUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/";
        }

        if (!user.getUsername().equals(username) && userRepository.existsByUsername(username)) {
            return "redirect:/user";
        }

        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            return "redirect:/user";
        }

        user.setUsername(username);
        user.setEmail(email);

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        userRepository.save(user);
        userService.setLoggedInUser(session, user);

        return "redirect:/user";
    }

    // Smazání účtu
    @PostMapping("/user/delete")
    public String deleteAccount(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/";
        }

        userRepository.delete(user);
        userService.logout(session);

        return "redirect:/";
    }
}