package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.CategoryDTO;
import com.nilemobile.backend.dto.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CreateCategoryRequest request);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);

    List<CategoryDTO> getAllCategoriesLevel(int level);

}
