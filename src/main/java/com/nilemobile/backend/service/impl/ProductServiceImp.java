package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.request.UpdateProductRequest;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.mapper.ProductMapper;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.dto.ProductDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.dto.request.CreateProductRequest;
import com.nilemobile.backend.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nilemobile.backend.exception.ErrorCode.INVALID_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
            

    @Transactional
    @Override
    public ProductDTO createProduct(CreateProductRequest request) {

        String firstLevelName = request.getFirstLevel().trim();
        final Category firstLevel = categoryRepository.findByNameAndLevel(firstLevelName, 1)
            .orElseGet(()->{
                Category newFirstLevel = new Category();
                newFirstLevel.setName(firstLevelName);
                newFirstLevel.setLevel(1);
                return categoryRepository.save(newFirstLevel);
            });

        String secondLevelName = request.getSecondLevel() != null ? request.getSecondLevel().trim() : "";
        Category secondLevel = null;
        if (!secondLevelName.isEmpty()) {    
            Optional<Category> secondLevelOpt = categoryRepository.findByNameAndParentCategory(secondLevelName, firstLevel);
            secondLevel = secondLevelOpt.orElseGet(() -> {
                Category newSecondLevel = new Category();
                newSecondLevel.setName(secondLevelName);
                newSecondLevel.setLevel(2);
                newSecondLevel.setParentCategory(firstLevel);
                return categoryRepository.save(newSecondLevel);
            });
        }
        
        String thirdLevelName = request.getThirdLevel() != null ? request.getThirdLevel().trim() : "";
        Category thirdLevel = null;
        if (!thirdLevelName.isEmpty()) {
            if (secondLevel == null) {
                throw new ProductException(
                        INVALID_PRODUCT,
                        "Third level category requires second level category to be present"
                );
            }

            Category parent = secondLevel;
            Optional<Category> thirdLevelOpt = categoryRepository.findByNameAndParentCategory(thirdLevelName, parent);
            thirdLevel = thirdLevelOpt.orElseGet(() -> {
                Category newThirdLevel = new Category();
                newThirdLevel.setName(thirdLevelName);
                newThirdLevel.setLevel(3);
                newThirdLevel.setParentCategory(parent);
                return categoryRepository.save(newThirdLevel);
            });
        }

        Product product = productMapper.toEntity(request);
        product.setCategory(thirdLevel != null ? thirdLevel : (secondLevel != null ? secondLevel : firstLevel));
        Product savedProduct =  productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(Long productId, UpdateProductRequest request) {
        Product product = findProductById(productId);
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ProductException(
                            ErrorCode.CATEGORY_NOT_FOUND,
                            "Category not found: " + request.getCategoryId()
                    ));
            product.setCategory(category);
        }

        Product updatedProduct = productMapper.partialUpdate(request, product);
        productRepository.save(updatedProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductException(
                    ErrorCode.PRODUCT_NOT_FOUND
            );
        }
    }

    @Transactional
    @Override
    public void deleteProductSoftly(Long productId) {
        Product product = findProductById(productId);
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ProductDTO getProductWithVariationsAndMethodById(Long productId) {
        if (productId == null || productId <= 0) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT, "Product ID must be positive");
        }
        
        var product = productRepository.findProductWithVariationsAndCategoryById(productId);
        if (product.isEmpty()) {
            throw new ProductException(
                    ErrorCode.PRODUCT_NOT_FOUND
            );
        }
        return productMapper.toDto(product.get());
    }


    @Override
    public Product findProductById(Long productId) {
        if (productId == null || productId <= 0) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT, "Product ID must be positive");
        }

        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(
                        ErrorCode.PRODUCT_NOT_FOUND
                ));
    }

    @Override
    public Page<ProductDTO> getAllProductsByCategory(String categoryName, int page, int size) {
        // Validate input
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT, "Category name must not be blank");
        }
        if (page < 0) {
            throw new ProductException(ErrorCode.VALIDATION_ERROR, "Page number must be >= 0");
        }
        if (size <= 0) {
            throw new ProductException(ErrorCode.VALIDATION_ERROR, "Page size must be > 0");
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.findByCategory_Name(categoryName.trim(), pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDTO> getProductsByCategory(String categoryName, int page, int size) {
        // Validate input
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new ProductException(ErrorCode.INVALID_PRODUCT, "Category name must not be blank");
        }
        if (page < 0) {
            throw new ProductException(ErrorCode.VALIDATION_ERROR, "Page number must be >= 0");
        }
        if (size <= 0) {
            throw new ProductException(ErrorCode.VALIDATION_ERROR, "Page size must be > 0");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.findByCategory_NameAndIsDeleted(categoryName.trim(), false, pageable);
        return productPage.map(productMapper::toDto);
    }

}
