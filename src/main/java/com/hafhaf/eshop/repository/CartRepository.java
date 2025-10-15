package com.hafhaf.eshop.repository;

import com.hafhaf.eshop.model.Cart;
import com.hafhaf.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    void deleteByUser(User user);
}