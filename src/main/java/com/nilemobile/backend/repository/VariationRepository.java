package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.dto.VariationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    public List<Variation> findByProduct(Product product);
    List<VariationDTO> findByProductId(Long productId);

    List<Variation> findByProduct_ProductIdAndIsDeletedFalse(Long productId);
}
