package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.reponse.AdminProductDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.request.AdminCreateProductRequest;
import com.nilemobile.backend.service.AdminProductService;
import com.nilemobile.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminProductServiceImp implements AdminProductService {
    @Override
    public List<AdminProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new AdminProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getCategory().getParentCategory().getParentCategory().getName(),
                        product.getCategory().getParentCategory().getName(),
                        product.getCategory().getName(),
                        product.getVariations().size(),
                        product.getScreenSize(),
                        product.getDisplayTech(),
                        product.getRefreshRate(),
                        product.getResolution(),
                        product.getFrontCamera(),
                        product.getBackCamera(),
                        product.getChipset(),
                        product.getCpu(),
                        product.getGpu(),
                        product.getBatteryCapacity(),
                        product.getChargingPort(),
                        product.getOs(),
                        product.getProductSize(),
                        product.getProductWeight(),
                        product.getDescription()))
                .toList();
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with ID: " + productId));
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, AdminProductDTO adminProductDTO) throws ProductException {
        if (adminProductDTO.getName() == null || adminProductDTO.getName().isEmpty() ||
                adminProductDTO.getDescription() == null || adminProductDTO.getDescription().isEmpty() ||
                adminProductDTO.getScreenSize() == null || adminProductDTO.getScreenSize() <= 0 ||
                adminProductDTO.getDisplayTech() == null || adminProductDTO.getDisplayTech().isEmpty() ||
                adminProductDTO.getResolution() == null || adminProductDTO.getResolution().isEmpty() ||
                adminProductDTO.getRefreshRate() == null || adminProductDTO.getRefreshRate().isEmpty() ||
                adminProductDTO.getFrontCamera() == null || adminProductDTO.getFrontCamera().isEmpty() ||
                adminProductDTO.getBackCamera() == null || adminProductDTO.getBackCamera().isEmpty() ||
                adminProductDTO.getChipset() == null || adminProductDTO.getChipset().isEmpty() ||
                adminProductDTO.getCpu() == null || adminProductDTO.getCpu().isEmpty() ||
                adminProductDTO.getGpu() == null || adminProductDTO.getGpu().isEmpty() ||
                adminProductDTO.getBatteryCapacity() == null || adminProductDTO.getBatteryCapacity() <= 0 ||
                adminProductDTO.getChargingPort() == null || adminProductDTO.getChargingPort().isEmpty() ||
                adminProductDTO.getOs() == null || adminProductDTO.getOs().isEmpty() ||
                adminProductDTO.getProductSize() == null || adminProductDTO.getProductSize().isEmpty() ||
                adminProductDTO.getProductWeight() == null || adminProductDTO.getProductWeight() <= 0 ||
                adminProductDTO.getCategory() == null || adminProductDTO.getCategory().isEmpty() ||
                adminProductDTO.getBrand() == null || adminProductDTO.getBrand().isEmpty() ||
                adminProductDTO.getSeries() == null || adminProductDTO.getSeries().isEmpty())
        {
            throw new ProductException("Thông tin cập nhật không được để trống.");
        }

        Product product = findProductById(productId);

        product.setName(adminProductDTO.getName());
        product.setDescription(adminProductDTO.getDescription());
        product.setScreenSize(adminProductDTO.getScreenSize());
        product.setDisplayTech(adminProductDTO.getDisplayTech());
        product.setResolution(adminProductDTO.getResolution());
        product.setRefreshRate(adminProductDTO.getRefreshRate());
        product.setFrontCamera(adminProductDTO.getFrontCamera());
        product.setBackCamera(adminProductDTO.getBackCamera());
        product.setChipset(adminProductDTO.getChipset());
        product.setCpu(adminProductDTO.getCpu());
        product.setGpu(adminProductDTO.getGpu());
        product.setBatteryCapacity(adminProductDTO.getBatteryCapacity());
        product.setChargingPort(adminProductDTO.getChargingPort());
        product.setOs(adminProductDTO.getOs());
        product.setProductSize(adminProductDTO.getProductSize());
        product.setProductWeight(adminProductDTO.getProductWeight());
        product.getCategory().getParentCategory().getParentCategory().setName(adminProductDTO.getCategory());
        product.getCategory().getParentCategory().setName(adminProductDTO.getBrand());
        product.getCategory().setName(adminProductDTO.getSeries());

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product with ID " + id + " not found"));
    }

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public AdminProductServiceImp(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(AdminCreateProductRequest request){
        if (request == null || request.getFirstLevel() == null || request.getFirstLevel().trim().isEmpty()) {
            throw new ProductException("First level category is required");
        }

        String firstLevelName = request.getFirstLevel().trim();
        Optional<Category> firstLevelOpt = categoryRepository.findByName(firstLevelName);
        Category firstLevel = firstLevelOpt.orElseGet(() -> {
            Category newFirstLevel = new Category();
            newFirstLevel.setName(firstLevelName);
            newFirstLevel.setLevel(1);
            return categoryRepository.save(newFirstLevel);
        });

        String secondLevelName = request.getSecondLevel() != null ? request.getSecondLevel().trim() : null;
        Category secondLevel = null;
        if (secondLevelName != null && !secondLevelName.isEmpty()) {
            Optional<Category> secondLevelOpt = categoryRepository.findByNameAndParentCategory(secondLevelName, firstLevel);
            secondLevel = secondLevelOpt.orElseGet(() -> {
                Category newSecondLevel = new Category();
                newSecondLevel.setName(secondLevelName);
                newSecondLevel.setLevel(2);
                newSecondLevel.setParentCategory(firstLevel);
                return categoryRepository.save(newSecondLevel);
            });
        }

        String thirdLevelName = request.getThirdLevel() != null ? request.getThirdLevel().trim() : null;
        Category thirdLevel = null;
        if (thirdLevelName != null && !thirdLevelName.isEmpty()) {
            Category parent = secondLevel != null ? secondLevel : firstLevel;
            Optional<Category> thirdLevelOpt = categoryRepository.findByNameAndParentCategory(thirdLevelName, parent);
            thirdLevel = thirdLevelOpt.orElseGet(() -> {
                Category newThirdLevel = new Category();
                newThirdLevel.setName(thirdLevelName);
                newThirdLevel.setLevel(3);
                newThirdLevel.setParentCategory(parent);
                return categoryRepository.save(newThirdLevel);
            });
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setScreenSize(request.getScreenSize());
        product.setDisplayTech(request.getDisplayTech());
        product.setResolution(request.getResolution());
        product.setRefreshRate(request.getRefreshRate());
        product.setFrontCamera(request.getFrontCamera());
        product.setBackCamera(request.getBackCamera());
        product.setChipset(request.getChipset());
        product.setCpu(request.getCpu());
        product.setGpu(request.getGpu());
        product.setBatteryCapacity(request.getBatteryCapacity());
        product.setChargingPort(request.getChargingPort());
        product.setOs(request.getOs());
        product.setProductSize(request.getProductSize());
        product.setProductWeight(request.getProductWeight());
        product.setCategory(thirdLevel != null ? thirdLevel : (secondLevel != null ? secondLevel : firstLevel));
        product.setCreateAt(LocalDateTime.now());

        return productRepository.save(product);
    }
}
