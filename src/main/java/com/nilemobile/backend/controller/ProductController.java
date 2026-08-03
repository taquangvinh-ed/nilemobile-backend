package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.dto.request.CreateProductRequest;
import com.nilemobile.backend.dto.request.UpdateProductRequest;
import com.nilemobile.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/products")
    public ApiResponse<?> createProduct(@RequestBody CreateProductRequest request) {
        var newProduct = productService.createProduct(request);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .body(newProduct)
                .build();
    }

    @PatchMapping("/admin/products/{productId}")
    public ApiResponse<?> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        var updatedProduct = productService.updateProduct(productId, request);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .body(updatedProduct)
                .build();
    }

    @DeleteMapping("/admin/products/{productId}/soft-delete")
    public ApiResponse<?> deleteProductSoftly(@PathVariable Long productId) {
        productService.deleteProductSoftly(productId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
    }



    @DeleteMapping("/admin/products/{productId}")
    public ApiResponse<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/products/{productId}")
    public ApiResponse<?> getProductById(@PathVariable Long productId) {
        var product = productService.getProductWithVariationsAndMethodById(productId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .body(product)
                .build();
    }

    @GetMapping("/products/category/{categoryName}/all-products")
    public ApiResponse<?> getAllProductsByCategory(@PathVariable String categoryName,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        var productsPage = productService.getAllProductsByCategory(categoryName, page, size);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .body(productsPage)
                .build();
    }

    @GetMapping("/products/category/{categoryName}/products")
    public ApiResponse<?> getProductsByCategory(@PathVariable String categoryName,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        var productsPage = productService.getProductsByCategory(categoryName, page, size);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .body(productsPage)
                .build();
    }

}
