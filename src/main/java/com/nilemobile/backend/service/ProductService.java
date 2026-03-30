package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.ProductDTO;
import com.nilemobile.backend.dto.request.CreateProductRequest;
import com.nilemobile.backend.dto.request.UpdateProductRequest;
import com.nilemobile.backend.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest request);

    ProductDTO updateProduct(Long productId, UpdateProductRequest request);

    void deleteProduct(Long productId);

    @Transactional
    void deleteProductSoftly(Long productId);

    ProductDTO getProductWithVariationsAndMethodById(Long productId);

    Product findProductById(Long productId);

    Page<ProductDTO> getAllProductsByCategory(String categoryName, int page, int size);

    Page<ProductDTO> getProductsByCategory(String categoryName, int page, int size);
}
