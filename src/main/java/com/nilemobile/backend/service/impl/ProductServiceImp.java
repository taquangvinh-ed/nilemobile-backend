package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.mapper.ProductMapper;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.reponse.ProductDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.request.CreateCategoryRequest;
import com.nilemobile.backend.request.CreateProductRequest;
import com.nilemobile.backend.service.ProductService;
import com.nilemobile.backend.service.UserService;
import com.nilemobile.backend.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
                        INVALID_PRODUCT.getMessage()
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
    
}
