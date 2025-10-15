package com.hafhaf.eshop.repository;

import com.hafhaf.eshop.model.Orders;
import com.hafhaf.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    //Objednávky uživatele
    List<Orders> findByUserOrderByCreatedAtDesc(User user);
}