package com.hafhaf.eshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "product_id"})
})
public class Cart {

    // Definice atributů
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private Integer quantity;

    // Konstrukce
    public Cart() {
    }

    public Cart(User user, Products product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    // Get
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Products getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Set

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    // Pomocné metody
    // Součet
    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    // Zvětšení počtu
    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    // Snížení počtu
    public void decreaseQuantity(int amount) {
        if (this.quantity - amount <= 0) {
            throw new IllegalArgumentException("Quantity would be zero or negative");
        }
        this.quantity -= amount;
    }
}