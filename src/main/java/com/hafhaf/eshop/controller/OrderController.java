package com.hafhaf.eshop.controller;

import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.service.OrderService;
import com.hafhaf.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    // Kontroler
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // Tvorba objednávky
    @PostMapping("/order/create")
    public String createOrder(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/cart";
        }

        boolean success = orderService.createOrder(session, user);

        if (success) {
            return "redirect:/user";
        } else {
            return "redirect:/cart";
        }
    }
}