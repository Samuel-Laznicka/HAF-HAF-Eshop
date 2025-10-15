package com.hafhaf.eshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "products")
public class Products {

    // Definice rolí
    public enum ProductCategory {
        ACCESORIES,
        FOOD,
        PETS,
        TOYS
    }

    // Definice atributů
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock = 10;

    @Column(nullable = true, length = 255)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductCategory category;

    @Column(nullable = false)
    private Integer discount = 0;

    // Konstruktory
    public Products() {
    }

    public Products(Long id, String username, String email, String password, Integer points, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.category = category;
        this.discount = discount;
    }

    // Get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getimageUrl() {
        return imageUrl;
    }

    public ProductCategory getcategory() {
        return category;
    }

    public Integer getDiscount() {
        return discount;
    }

    // Set
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setDiscount(Integer discount) {
        if (discount == null || discount < 0) {
            this.discount = 0;
        } else if (discount > 100) {
            this.discount = 100;
        } else {
            this.discount = discount;
        }
    }

    //Pomocné metody
    //Výpočet ceny po slevě
    public BigDecimal getDiscountedPrice() {
        if (discount == null || discount <= 0) {
            return price;
        }
        BigDecimal discountMultiplier = BigDecimal.valueOf(1 - discount / 100.0);
        return price.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    //Zjištění slevy
    public boolean isOnSale() {
        return discount != null && discount > 0;
    }

    //Obrázek podle kategorie
    public String getCategoryImage() {
        return switch (this.category) {
            case FOOD -> "/images/food.png";
            case TOYS -> "/images/toys.png";
            case ACCESORIES -> "/images/accesories.png";
            case PETS -> "/images/pets.png";
        };
    }

    //Snížení Stock
    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.stock < amount) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        this.stock -= amount;
    }

    //Dostupnost
    public boolean isInStock() {
        return stock != null && stock > 0;
    }

}