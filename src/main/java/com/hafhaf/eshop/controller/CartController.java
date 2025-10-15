package com.hafhaf.eshop.controller;

import com.hafhaf.eshop.service.CartService;
import com.hafhaf.eshop.service.OrderService;
import com.hafhaf.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    // Kontroler
    public CartController(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    // Přidat jeden
    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            cartService.addToCart(session, productId);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart";
    }

    // Odebrat jeden
    @PostMapping("/cart/remove-one/{productId}")
    public String removeOne(@PathVariable Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            cartService.removeOne(session, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart";
    }

    // Remove produkt
    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            cartService.removeFromCart(session, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart";
    }

    // Smazání košíku
    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            cartService.clearCart(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart";
    }

    // Dokončení objednávky
    @PostMapping("/cart/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            cartService.validateCartStock(session);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            return "redirect:/cart";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cart";
        }
    }
}