package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.request.CreateCategoryRequest;
import com.nilemobile.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Categories createBrand(CreateCategoryRequest request) {
        if (request.getCategoryName() == null || request.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }

        Categories parentCategory = categoryRepository.findByName("Smartphone")
                .orElseThrow(() -> new IllegalArgumentException("Parent category 'Smartphone' not found"));
        Categories newBrand = new Categories();
        newBrand.setName(request.getCategoryName());
        newBrand.setParentCategory(parentCategory);
        newBrand.setLevel(2);

        return categoryRepository.save(newBrand);
    }

    @Override
    public Categories updateBrand(Long id, CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }

        Categories brand = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        brand.setName(categoryDTO.getCategoryName());
        return categoryRepository.save(brand);
    }

    @Override
    public void deleteBrandById(Long id) {
        Categories brand = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        // Delete all series associated with the brand
        List<Categories> seriesList = categoryRepository.findByParentCategory(brand);
        for (Categories series : seriesList) {
            categoryRepository.delete(series);
        }
        categoryRepository.delete(brand);
    }

    @Override
    public List<CategoryDTO> getAllBrands() {
        List<Long> parentIds = categoryRepository.findParentIdsByName("Smartphone");
        return categoryRepository.findByLevel(2).stream()
                .filter(category -> parentIds.contains(category.getParentCategory().getCategories_id()))
                .map(category -> new CategoryDTO(
                        category.getCategories_id(),
                        category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Categories createSeries(Long brandId, CreateCategoryRequest request) {
        if (request.getCategoryName() == null || request.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Series name cannot be empty");
        }
        Categories brand = categoryRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        Categories newSeries = new Categories();
        newSeries.setName(request.getCategoryName());
        newSeries.setParentCategory(brand);
        newSeries.setLevel(3);

        return categoryRepository.save(newSeries);
    }

    @Override
    public Categories updateSeries(Long id, CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Series name cannot be empty");
        }
        Categories series = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Series not found"));
        series.setName(categoryDTO.getCategoryName());
        return categoryRepository.save(series);
    }

    @Override
    public void deleteSeriesById(Long id) {
        Categories series = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Series not found"));
        categoryRepository.delete(series);
    }

    @Override
    public List<CategoryDTO> getAllSeries(Long brandId){
        Categories categories = categoryRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        return categoryRepository.findByLevel(3).stream()
                .filter(category -> category.getParentCategory().getCategories_id().equals(brandId))
                .map(category -> new CategoryDTO(
                        category.getCategories_id(),
                        category.getName()))
                .collect(Collectors.toList());
    }
}
