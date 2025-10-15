package com.hafhaf.eshop;

import com.hafhaf.eshop.model.Products;
import com.hafhaf.eshop.model.User;
import com.hafhaf.eshop.repository.ProductRepository;
import com.hafhaf.eshop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DataSeeder(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() > 0) {
            System.out.println("Data již existují, seed přeskočen.");
            return;
        }

        System.out.println("Spouštím seed data...");

        Products p1 = new Products();
        p1.setName("Luxusní obojek");
        p1.setDescription("Ručně vyráběný obojek pro psy");
        p1.setPrice(new BigDecimal("299.00"));
        p1.setStock(15);
        p1.setCategory(Products.ProductCategory.ACCESORIES);
        p1.setDiscount(10);

        Products p2 = new Products();
        p2.setName("Premium granule");
        p2.setDescription("Vysoce kvalitní krmivo pro psy");
        p2.setPrice(new BigDecimal("599.00"));
        p2.setStock(25);
        p2.setCategory(Products.ProductCategory.FOOD);
        p2.setDiscount(0);

        Products p3 = new Products();
        p3.setName("Plyšová hračka");
        p3.setDescription("Měkká hračka pro malá štěňata");
        p3.setPrice(new BigDecimal("149.00"));
        p3.setStock(30);
        p3.setCategory(Products.ProductCategory.TOYS);
        p3.setDiscount(20);

        Products p4 = new Products();
        p4.setName("Vodítko");
        p4.setDescription("Pevné vodítko 2m");
        p4.setPrice(new BigDecimal("199.00"));
        p4.setStock(20);
        p4.setCategory(Products.ProductCategory.ACCESORIES);
        p4.setDiscount(0);

        Products p5 = new Products();
        p5.setName("Konzerva pro psy");
        p5.setDescription("Masová konzerva s hovězím");
        p5.setPrice(new BigDecimal("89.00"));
        p5.setStock(50);
        p5.setCategory(Products.ProductCategory.FOOD);
        p5.setDiscount(15);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        productRepository.save(p5);

        // Testovacího uživatel (admin)
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@eshop.cz");
        admin.setPassword("admin123");
        admin.setPoints(new BigDecimal("10000.00"));
        admin.setRole(User.UserRole.ADMIN);
        userRepository.save(admin);

        // Testovací zákazník
        User customer = new User();
        customer.setUsername("customer");
        customer.setEmail("customer@eshop.cz");
        customer.setPassword("customer123");
        customer.setPoints(new BigDecimal("500.00"));
        customer.setRole(User.UserRole.CUSTOMER);
        userRepository.save(customer);

        System.out.println("Seed data úspěšně vytvořena!");
    }
}