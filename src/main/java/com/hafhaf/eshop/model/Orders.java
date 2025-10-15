package com.hafhaf.eshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    // Definice atributů
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    // Nastavení timestamp
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Konstruktory
    public Orders() {
    }

    public Orders(User user, BigDecimal totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }

    // Get
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    // Set
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    // Pomocné metody
    // Přidat
    public void addOrderItem(OrderItems item) {
        orderItems.add(item);
        item.setOrders(this);
    }

    // Odebrat
    public void removeOrderItem(OrderItems item) {
        orderItems.remove(item);
        item.setOrders(null);
    }
}