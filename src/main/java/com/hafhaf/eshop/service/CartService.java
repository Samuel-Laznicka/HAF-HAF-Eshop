package com.hafhaf.eshop.service;

import com.hafhaf.eshop.model.Products;
import com.hafhaf.eshop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Získání košíku
    @SuppressWarnings("unchecked")
    private Map<Long, Integer> getCart(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    // Přidat jeden
    public void addToCart(HttpSession session, Long productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nenalezen"));

        if (!product.isInStock()) {
            throw new IllegalArgumentException("Produkt není skladem");
        }

        Map<Long, Integer> cart = getCart(session);
        int currentAmount = cart.getOrDefault(productId, 0);
        int newAmount = currentAmount + 1;

        if (product.getStock() < newAmount) {
            throw new IllegalArgumentException("Nedostatečné množství na skladě");
        }

        cart.put(productId, newAmount);
        session.setAttribute("cart", cart);
    }

    // Odebrat jeden
    public void removeOne(HttpSession session, Long productId) {
        Map<Long, Integer> cart = getCart(session);

        if (cart.containsKey(productId)) {
            int currentAmount = cart.get(productId);
            if (currentAmount > 1) {
                cart.put(productId, currentAmount - 1);
            } else {
                cart.remove(productId);
            }
            session.setAttribute("cart", cart);
        }
    }

    // Odstranění produktu
    public void removeFromCart(HttpSession session, Long productId) {
        Map<Long, Integer> cart = getCart(session);
        cart.remove(productId);
        session.setAttribute("cart", cart);
    }

    // Clear
    public void clearCart(HttpSession session) {
        session.removeAttribute("cart");
    }

    // Validace
    public void validateCartStock(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);

        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Košík je prázdný");
        }

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Products product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Produkt nenalezen"));

            if (!product.isInStock()) {
                throw new IllegalArgumentException("Produkt " + product.getName() + " není skladem");
            }

            if (product.getStock() < entry.getValue()) {
                throw new IllegalArgumentException(
                        "Nedostatečné množství produktu " + product.getName() +
                                " (skladem: " + product.getStock() + ", požadováno: " + entry.getValue() + ")"
                );
            }
        }
    }

    // Snížení stock
    @Transactional
    public void decreaseStockForCart(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Products product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Produkt nenalezen"));

            product.decreaseStock(entry.getValue());
            productRepository.save(product);
        }
    }

    // Košík s produkty
    public Map<Products, Integer> getCartWithProducts(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);
        Map<Products, Integer> cartWithProducts = new HashMap<>();

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Products product = productRepository.findById(entry.getKey()).orElse(null);
            if (product != null) {
                cartWithProducts.put(product, entry.getValue());
            }
        }

        return cartWithProducts;
    }

    // Cena celkem
    public BigDecimal getTotalPrice(HttpSession session) {
        Map<Products, Integer> cartWithProducts = getCartWithProducts(session);
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Products, Integer> entry : cartWithProducts.entrySet()) {
            Products product = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            total = total.add(itemTotal);
        }

        return total;
    }

    // Počet položek
    public int getCartItemCount(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }
}