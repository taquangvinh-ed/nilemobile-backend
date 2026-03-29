package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.CategoryAlreadyExistedException;
import com.nilemobile.backend.exception.CategoryNotFoundException;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.mapper.CategoriesMapper;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.request.CreateCategoryRequest;
import com.nilemobile.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoriesMapper categoriesMapper;

    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        Optional<Category> brandOpt = categoryRepository.findBrandByNameAndLevel(request.getCategoryName());
        if(brandOpt.isPresent()){
            throw new CategoryAlreadyExistedException(ErrorCode.CATEGORY_ALREADY_EXISTED.getMessage());
        }

        Category newCategory = categoriesMapper.toEntity(request);
        if(request.getCategoryParentId() != null){
            Category parentCategory = categoryRepository.findById(request.getCategoryParentId())
                    .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage()));
            newCategory.setParentCategory(parentCategory);
        }
         Category savedCategory = categoryRepository.save(newCategory);
        return categoriesMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage()));
        Category updatedCategoryDTO = categoriesMapper.partialUpdate(categoryDTO, category);
        Category savedCategory = categoryRepository.save(updatedCategoryDTO);
        return categoriesMapper.toDto(savedCategory);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage()));

        List<Category> categories = categoryRepository.findByParentCategory(category);
        if (categories != null){
            categoryRepository.deleteAll(categories);
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesLevel1() {
        return categoryRepository.findByLevel(1).stream()
                .map(categoriesMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<CategoryDTO> getAllCategoriesByParentCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage()));

        return categoryRepository.findByParentCategory(category).stream()
                .map(categoriesMapper::toDto)
                .collect(Collectors.toList());
    }
}
