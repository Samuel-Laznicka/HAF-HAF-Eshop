package com.hafhaf.eshop.service;

import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final String USER_SESSION_KEY = "loggedInUser";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registrace
    public boolean register(String username, String email, String password) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return false;
        }

        // Nový user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPoints(new BigDecimal("500.00"));
        user.setRole(User.UserRole.CUSTOMER);

        userRepository.save(user);
        return true;
    }

    // Login
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt.get();
        }
        return null;
    }

    // Ulož uživatele
    public void setLoggedInUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    // Logged in user
    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    // Odhlášení
    public void logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    // Body +
    public void addPoints(User user, BigDecimal amount) {
        user.setPoints(user.getPoints().add(amount));
        userRepository.save(user);
    }
}