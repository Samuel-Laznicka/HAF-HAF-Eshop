package com.hafhaf.eshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user")
public class User {

    // Definice rolí
    public enum UserRole {
        CUSTOMER,
        ADMIN
    }

    // Definice atributů
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal points = BigDecimal.valueOf(500.00);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.CUSTOMER;

    // Konstruktory
    public User() {
    }

    public User(Long id, String username, String email, String password, BigDecimal points, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.points = points;
        this.role = role;
    }

    // Get
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public UserRole getRole() {
        return role;
    }

    // Set
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}