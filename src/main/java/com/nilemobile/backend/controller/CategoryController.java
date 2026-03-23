package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.request.CreateCategoryRequest;
import com.nilemobile.backend.service.impl.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("/brand/get-all")
    public List<CategoryDTO> getAllBrands(){
        return categoryServiceImp.getAllBrands();
    }

    @PostMapping("/brand/add")
    public Categories createBrand(@RequestBody CreateCategoryRequest request) {
        return categoryServiceImp.createBrand(request);
    }

    @PutMapping("/brand/update/{id}")
    public ResponseEntity<CategoryDTO> updateBrand(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Categories updatedBrand = categoryServiceImp.updateBrand(id, categoryDTO);
        return ResponseEntity.ok(new CategoryDTO(updatedBrand));
    }

    @DeleteMapping("/brand/delete/{id}")
    public void deleteBrand(@PathVariable Long id) {
        categoryServiceImp.deleteBrandById(id);
    }

    @GetMapping("/series/get-all/{brandId}")
    public List<CategoryDTO> getAllSeriesByBrand(@PathVariable Long brandId){
        return categoryServiceImp.getAllSeries(brandId);
    }

    @PostMapping("/series/add/{brandId}")
    public Categories createSeries(@PathVariable Long brandId, @RequestBody CreateCategoryRequest request) {
        return categoryServiceImp.createSeries(brandId, request);
    }

    @PutMapping("/series/update/{id}")
    public ResponseEntity<CategoryDTO> updateSeries(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Categories updatedSeries = categoryServiceImp.updateSeries(id, categoryDTO);
        return ResponseEntity.ok(new CategoryDTO(updatedSeries));
    }

    @DeleteMapping("/series/delete/{id}")
    public void deleteSeries(@PathVariable Long id) {
        categoryServiceImp.deleteSeriesById(id);
    }
}
