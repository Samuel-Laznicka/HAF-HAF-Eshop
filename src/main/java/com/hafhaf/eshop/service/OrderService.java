package com.hafhaf.eshop.service;

import com.hafhaf.eshop.model.Orders;
import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.repository.OrdersRepository;
import org.springframework.stereotype.Service;
import com.hafhaf.eshop.model.OrderItems;
import com.hafhaf.eshop.model.Products;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrdersRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;

    public OrderService(OrdersRepository orderRepository, CartService cartService, UserService userService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userService = userService;
    }

    // Základní operace
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public Orders findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Orders save(Orders order) {
        return orderRepository.save(order);
    }

    // Objednávka z košíku
    @Transactional
    public boolean createOrder(HttpSession session, User user) {
        // Získat položky z košíku
        Map<Products, Integer> cartItems = cartService.getCartWithProducts(session);

        // Celkovova cena
        BigDecimal totalPrice = cartService.getTotalPrice(session);

        // Dostupnost
        cartService.validateCartStock(session);

        // Body
        if (user.getPoints().compareTo(totalPrice) < 0) {
            return false;
        }

        // Create Objednávka
        Orders order = new Orders(user, totalPrice);

        // Přidat položky objednávky
        for (Map.Entry<Products, Integer> entry : cartItems.entrySet()) {
            Products product = entry.getKey();
            Integer quantity = entry.getValue();

            OrderItems orderItem = new OrderItems(order, product, quantity, product.getPrice());
            order.addOrderItem(orderItem);
        }

        // Snížit stock pro všechny produkty
        cartService.decreaseStockForCart(session);

        // Odečíst body
        user.setPoints(user.getPoints().subtract(totalPrice));

        // Uložit objednávku a uživatele
        orderRepository.save(order);
        userService.setLoggedInUser(session, user);

        // Prazdný košík
        cartService.clearCart(session);

        return true;
    }

    // Objednávky
    public List<Orders> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
}