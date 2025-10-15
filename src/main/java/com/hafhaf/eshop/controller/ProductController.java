package com.hafhaf.eshop.controller;

import com.hafhaf.eshop.model.Products;
import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.repository.ProductRepository;
import com.hafhaf.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final UserService userService;

    // Kontroler
    public ProductController(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    // Přidání produktu
    @PostMapping("/product/add")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer stock,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") Integer discount,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

    User user = userService.getLoggedInUser(session);

    if (user == null || user.getRole() != User.UserRole.ADMIN) {
        return "redirect:/";
    }

    try {
        Products product = new Products();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setDiscount(discount);

        Products.ProductCategory cat = Products.ProductCategory.valueOf(category);
        product.setCategory(cat);

        product.setImageUrl(product.getCategoryImage());

        productRepository.save(product);

    } catch (IllegalArgumentException e) {
    } catch (Exception e) {
        e.printStackTrace();
    }

    return "redirect:/";
    }

    // Úprava produktu
    @PostMapping("/product/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam Integer stock,
            @RequestParam(defaultValue = "0") Integer discount,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userService.getLoggedInUser(session);

        if (user == null || user.getRole() != User.UserRole.ADMIN) {
            return "redirect:/";
        }

        Products product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/";
        }

        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setDiscount(discount);

        productRepository.save(product);

        return "redirect:/";
    }

    // Smazání produktu
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userService.getLoggedInUser(session);

        if (user == null || user.getRole() != User.UserRole.ADMIN) {
            return "redirect:/";
        }

        productRepository.deleteById(id);

        return "redirect:/";
    }
}