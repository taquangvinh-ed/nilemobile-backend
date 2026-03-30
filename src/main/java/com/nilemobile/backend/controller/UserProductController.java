package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.reponse.ProductResponseDTO;
import com.nilemobile.backend.dto.request.CreateProductRequest;
import com.nilemobile.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/admin/products")
    public ResponseEntity<ProductResponseDTO> createProductHandler(@RequestBody CreateProductRequest request) throws ProductException {
        Product product = productService.createProduct(request);
        ProductResponseDTO productDTO = new ProductResponseDTO(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }


    @GetMapping("/products/id/{productId}")
    public ResponseEntity<ProductResponseDTO> findProductByIdHanlder(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);

        ProductResponseDTO productDTO = new ProductResponseDTO(product);

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/products/filter")
    public ResponseEntity<Page<ProductResponseDTO>> filterProducts(
            @RequestParam(required = false) String firstLevel,
            @RequestParam(required = false) String secondLevel,
            @RequestParam(required = false) String thirdLevel,
            @RequestParam(required = false) List<String> ram,
            @RequestParam(required = false) List<String> rom,
            @RequestParam(required = false) String os,
            @RequestParam(required = false) Integer minBattery,
            @RequestParam(required = false) Integer maxBattery,
            @RequestParam(required = false) Float minScreenSize,
            @RequestParam(required = false) Float maxScreenSize,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) throws ProductException {

        System.out.println("Filtering products with parameters:");
        System.out.println("firstLevel: " + firstLevel);
        System.out.println("secondLevel: " + secondLevel);
        System.out.println("thirdLevel: " + thirdLevel);
        System.out.println("ram: " + ram);
        System.out.println("rom: " + rom);
        System.out.println("os: " + os);
        System.out.println("minBattery: " + minBattery);
        System.out.println("maxBattery: " + maxBattery);
        System.out.println("minScreenSize: " + minScreenSize);
        System.out.println("maxScreenSize: " + maxScreenSize);
        System.out.println("minPrice: " + minPrice);
        System.out.println("maxPrice: " + maxPrice);
        System.out.println("minDiscount: " + minDiscount);
        System.out.println("sort: " + sort);
        System.out.println("pageNumber: " + pageNumber);
        System.out.println("pageSize: " + pageSize);

        Page<Product> filteredProducts = productService.getAllProducts(firstLevel, secondLevel, thirdLevel,
                ram, rom, os, minBattery, maxBattery, minScreenSize, maxScreenSize,
                minPrice, maxPrice, minDiscount, sort, pageNumber, pageSize);

        Page<ProductResponseDTO> productResponseDTOs = filteredProducts.map(ProductResponseDTO::new);

        return ResponseEntity.ok(productResponseDTOs);
    }

    @GetMapping("/products/third-levels")
    public ResponseEntity<List<String>> getAllThirdLevels() {
        List<String> thirdLevels = productService.getAllThirdLevels();
        return ResponseEntity.ok(thirdLevels);
    }

    @GetMapping("/products/second-levels")
    public ResponseEntity<List<String>> getAllSecondLevels() {
        List<String> secondLevels = productService.getAllSecondLevels();
        return ResponseEntity.ok(secondLevels);
    }

    @GetMapping("/product/getThirdLevel")
    public ResponseEntity<List<String>> getAllThirdLevelOfSecondLevel(@RequestParam String secondLevel) {
        List<String> allThirdLevels = productService.getThirdLevels(secondLevel);
        return ResponseEntity.ok(allThirdLevels);
    }

    @GetMapping("/products/filter-simple")
    public ResponseEntity<Page<ProductResponseDTO>> filterByPriceBatteryAndScreenSize(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minBattery,
            @RequestParam(required = false) Integer maxBattery,
            @RequestParam(required = false) Float minScreenSize,
            @RequestParam(required = false) Float maxScreenSize,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String secondLevel,
            @RequestParam(required = false) String thirdLevel,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) throws ProductException {

//        System.out.println("Filtering products with simple parameters:");
//        System.out.println("minBattery: " + minBattery);
//        System.out.println("maxBattery: " + maxBattery);
//        System.out.println("minScreenSize: " + minScreenSize);
//        System.out.println("maxScreenSize: " + maxScreenSize);
//        System.out.println("minPrice: " + minPrice);
//        System.out.println("maxPrice: " + maxPrice);
//        System.out.println("sort: " + sort);
//        System.out.println("pageNumber: " + pageNumber);
//        System.out.println("pageSize: " + pageSize);

        Page<Product> filteredProducts = productService.filterByPriceBatteryAndScreenSize(
                keyword,
                minBattery, maxBattery,
                minScreenSize, maxScreenSize,
                minPrice, maxPrice, secondLevel, thirdLevel,
                sort, pageNumber, pageSize
        );

        Page<ProductResponseDTO> productResponseDTOs = filteredProducts.map(ProductResponseDTO::new);

        return ResponseEntity.ok(productResponseDTOs);
    }


}
