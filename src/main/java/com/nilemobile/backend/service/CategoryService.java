package com.nilemobile.backend.service;

import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CreateCategoryRequest request);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);

    List<CategoryDTO> getAllCategoriesLevel1();

    List<CategoryDTO> getAllCategoriesByParentCategory(Long brandId);
}
