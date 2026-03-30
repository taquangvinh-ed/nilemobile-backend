package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.dto.AdminProductDTO;
import com.nilemobile.backend.reponse.ProductResponseDTO;
import com.nilemobile.backend.dto.request.AdminCreateProductRequest;
import com.nilemobile.backend.dto.request.CreateProductRequest;
import com.nilemobile.backend.service.AdminProductService;
import com.nilemobile.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/product")

public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @Autowired
    private ProductService productService;

    @GetMapping("/id/{productId}")
    public ResponseEntity<AdminProductDTO> getProductById(@PathVariable Long productId) throws ProductException{
        Product product = adminProductService.findProductById(productId);
        AdminProductDTO adminProductDTO = new AdminProductDTO(product);
        return ResponseEntity.ok(adminProductDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<AdminProductDTO> createProductHandler(@RequestBody AdminCreateProductRequest request) throws ProductException {
        Product product = adminProductService.createProduct(request);
        AdminProductDTO adminProductDTO = new AdminProductDTO(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminProductDTO);
    }

    @PostMapping("/create-model")
    public ResponseEntity<List<ProductResponseDTO>> createProductsHandler(@RequestBody List<CreateProductRequest> requests) throws ProductException {
        List<ProductResponseDTO> createdProducts = new ArrayList<>();
        for (CreateProductRequest request : requests) {
            Product product = productService.createProduct(request);
            ProductResponseDTO productDTO = new ProductResponseDTO(product);
            createdProducts.add(productDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProducts);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AdminProductDTO>> getAllProducts() {
        List<AdminProductDTO> products = adminProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<AdminProductDTO> updateProductInfo(@PathVariable Long productId, @Valid @RequestBody AdminProductDTO adminProductDTO) throws ProductException {
        Product updatedProduct = adminProductService.updateProduct(productId, adminProductDTO);
        return ResponseEntity.ok(new AdminProductDTO(updatedProduct));
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProductById(@PathVariable Long productId) throws ProductException {
        adminProductService.deleteProduct(productId);
    }
}
