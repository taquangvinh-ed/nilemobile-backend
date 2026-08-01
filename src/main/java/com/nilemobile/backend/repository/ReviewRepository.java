package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Review;
import com.nilemobile.backend.model.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVariation(Variation variation);

    @Query("SELECT r FROM Review r JOIN FETCH r.customer WHERE r.variation = :variation")
    List<Review> findByVariationWithUser(@Param("variation") Variation variation);

    @Query("SELECT r FROM Review r JOIN FETCH r.variation v JOIN FETCH v.product p WHERE p = :product")
    List<Review> findByProduct(Product product);
}
