package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

   Optional<Product> findProductWithVariationsAndCategoryById(Long productId);

   Page<Product> findByCategory_NameAndIsDeleted(String categoryName, boolean deleted, Pageable pageable);

   Page<Product> findByCategory_Name(String categoryName, Pageable pageable);


}
