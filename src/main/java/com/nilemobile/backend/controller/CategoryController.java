package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.reponse.ApiResponse;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.request.CreateCategoryRequest;
import com.nilemobile.backend.service.CategoryService;
import com.nilemobile.backend.service.impl.CategoryServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<?> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryDTO createdCategory = categoryService.createCategory(request);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .body(createdCategory)
                .build();
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .body(updatedCategory)
                .build();
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/level1")
    public ApiResponse<List<CategoryDTO>> getAllCategoriesLevel1() {
        List<CategoryDTO> categories = categoryService.getAllCategoriesLevel1();
        return ApiResponse.<List<CategoryDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .body(categories)
                .build();
}

    @GetMapping("/parent/{parentId}")
    public ApiResponse<List<CategoryDTO>> getAllCategoriesByParentCategory(@PathVariable Long parentId) {
        List<CategoryDTO> categories = categoryService.getAllCategoriesByParentCategory(parentId);
        return ApiResponse.<List<CategoryDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .body(categories)
                .build();
    }
}
