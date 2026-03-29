package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.reponse.ProductDTO;
import com.nilemobile.backend.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public ProductDTO createProduct(CreateProductRequest request);

    public void deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product updatedProduct) throws ProductException;

    public Product findProductById(Long id) throws ProductException;


    public Product addVariation(Long productId, Variation variation) throws ProductException;

    public List<Product> findProductsByVariation(String ram, String rom, String color) throws ProductException;

    public List<Product> findProductsByCategory(String categoryID);

    public Variation updateVariation(Long productId, Long variationId, Variation updatedVariation) throws ProductException;

    public void deleteVariation(Long productId, Long variationId) throws ProductException;


    public Page<Product> getAllProducts(String firstLevel, String secondLevel, String thirdLevel,
                                        List<String> ram, List<String> rom, String os,
                                        Integer minBattery, Integer maxBattery, Float minScreenSize, Float maxScreenSize,
                                        Long minPrice, Long maxPrice, Integer minDiscount, String sort,
                                        Integer pageNumber, Integer pageSize);

    List<String> getAllThirdLevels();

    List<String> getAllSecondLevels();

    List<String> getThirdLevels(String secondLevel);

    Page<Product> filterByPriceBatteryAndScreenSize(
            String keyword,
            Integer minBattery, Integer maxBattery,
            Float minScreenSize, Float maxScreenSize,
            Long minPrice, Long maxPrice, String secondLevel, String thirdLevel,
            String sort, Integer pageNumber, Integer pageSize);
}
