package com.hafhaf.eshop.controller;

import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.repository.OrdersRepository;
import com.hafhaf.eshop.repository.ProductRepository;
import com.hafhaf.eshop.service.CartService;
import com.hafhaf.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    // Kontroler
    public PageController(CartService cartService, UserService userService, ProductRepository productRepository, OrdersRepository ordersRepository) {
        this.cartService = cartService;
        this.userService = userService;
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
    }

    // Index strana
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        User user = userService.getLoggedInUser(session);

        model.addAttribute("cssFile", "styleIndex.css");
        model.addAttribute("pageTitle", "Haf Haf Domov");
        model.addAttribute("user", user);

        // Načtení všech produktů
        model.addAttribute("products", productRepository.findAll());

        return "index";
    }

    // Stránka košíku - user only
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        User user = userService.getLoggedInUser(session);

        model.addAttribute("cssFile", "styleCart.css");
        model.addAttribute("pageTitle", "Košík");
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartService.getCartWithProducts(session));
        model.addAttribute("totalPrice", cartService.getTotalPrice(session));

        return "cart";
    }

    @GetMapping("/user")
    public String user(HttpSession session, Model model) {
        User user = userService.getLoggedInUser(session);
        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("cssFile", "styleUser.css");
        model.addAttribute("pageTitle", "Uživatel");
        model.addAttribute("user", user);

        model.addAttribute("orders", ordersRepository.findByUserOrderByCreatedAtDesc(user));

        return "user";
    }

    // Pop-up redirects
    @GetMapping("/popup/{type}")
    public String getPopup(@PathVariable String type) {
        return switch (type) {
            case "login" -> "fragments/popups :: login";
            case "register" -> "fragments/popups :: register";
            case "add-money" -> "fragments/popups :: add-money";
            case "edit-product" -> "fragments/popups :: edit-product";
            case "add-product" -> "fragments/popups :: add-product";
            case "delete-product" -> "fragments/popups :: delete-product";
            case "edit-user" -> "fragments/popups :: edit-user";
            case "delete-user" -> "fragments/popups :: delete-user";
            case "payment-confirm" -> "fragments/popups :: payment-confirm";
            case "delete-cart" -> "fragments/popups :: delete-cart";
            default -> null;
        };
    }
}