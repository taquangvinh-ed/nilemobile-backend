package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerCustomerId(Long customerId);
}
