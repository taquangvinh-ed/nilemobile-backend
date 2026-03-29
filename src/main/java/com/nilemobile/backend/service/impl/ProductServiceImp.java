package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.request.CreateProductRequest;
import com.nilemobile.backend.service.ProductService;
import com.nilemobile.backend.service.UserService;
import com.nilemobile.backend.specification.ProductSpecification;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
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

        // Xử lý variations
        if (request.getVariations() != null && !request.getVariations().isEmpty()) {
            for (Variation variation : request.getVariations()) {
                variation.setId(null); // Đảm bảo tạo mới
                variation.setProduct(product); // Liên kết với product
                product.getVariations().add(variation);
            }
        }

        return productRepository.save(product);
    }

    // Hàm parse số
    private Float parseFloat(String value, String fieldName) {
        try {
            return value != null && !value.trim().isEmpty() ? Float.parseFloat(value) : null;
        } catch (NumberFormatException e) {
            throw new ProductException(fieldName + " phải là số hợp lệ");
        }
    }

    private Integer parseInt(String value, String fieldName) {
        try {
            return value != null && !value.trim().isEmpty() ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            throw new ProductException(fieldName + " phải là số nguyên hợp lệ");
        }
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductException("Product with ID" + productId + "is not found");
        }

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        if (product == null) {
            throw new ProductException("Update product cannot be null");
        }

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getScreenSize() != null) {
            existingProduct.setScreenSize(product.getScreenSize());
        }
        if (product.getDisplayTech() != null) {
            existingProduct.setDisplayTech(product.getDisplayTech());
        }
        if (product.getResolution() != null) {
            existingProduct.setResolution(product.getResolution());
        }
        if (product.getRefreshRate() != null) {
            existingProduct.setRefreshRate(product.getRefreshRate());
        }
        if (product.getFrontCamera() != null) {
            existingProduct.setFrontCamera(product.getFrontCamera());
        }
        if (product.getBackCamera() != null) {
            existingProduct.setBackCamera(product.getBackCamera());
        }
        if (product.getChipset() != null) {
            existingProduct.setChipset(product.getChipset());
        }

        if (product.getCpu() != null) {
            existingProduct.setChipset(product.getCpu());
        }

        if (product.getGpu() != null) {
            existingProduct.setChipset(product.getGpu());
        }

        if (product.getBatteryCapacity() != null) {
            existingProduct.setBatteryCapacity(product.getBatteryCapacity());
        }
        if (product.getChargingPort() != null) {
            existingProduct.setChargingPort(product.getChargingPort());
        }
        if (product.getOs() != null) {
            existingProduct.setOs(product.getOs());
        }
        if (product.getProductSize() != null) {
            existingProduct.setProductSize(product.getProductSize());
        }
        if (product.getProductWeight() != null) {
            existingProduct.setProductWeight(product.getProductWeight());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }

        //        // Xử lý cập nhật danh mục (Categories)
        //        if (product.getCategories() != null && product.getCategories().getCategories_id() != null) {
        //            Long categoryId = product.getCategories().getCategories_id();
        //            Categories category = categoryRepository.findById(categoryId)
        //                    .orElseThrow(() -> new ProductException("Category with ID " + categoryId + " not found"));
        //            existingProduct.setCategories(category);
        //        }


        return productRepository.save(existingProduct);

    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product with ID " + id + " not found"));
    }

    @Override
    public Product addVariation(Long productId, Variation variation) throws ProductException {
        Product product = findProductById(productId);

        if (variation == null) {
            throw new ProductException("Biến thể không thể null");
        }
        if (variation.getRam() == null || variation.getRom() == null || variation.getPrice() == null) {
            throw new ProductException("RAM, ROM và giá là bắt buộc cho biến thể");
        }
        if (variation.getStockQuantity() == null || variation.getStockQuantity() < 0) {
            throw new ProductException("Số lượng tồn kho phải >= 0");
        }

        product.getVariations().add(variation);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findProductsByCategory(String categoryID) {
        return List.of();
    }

    @Override
    public Variation updateVariation(Long productId, Long variationId, Variation updatedVariation) throws ProductException {
        return null;
    }

    @Override
    public void deleteVariation(Long productId, Long variationId) throws ProductException {

    }

    @Override
    public List<Product> findProductsByVariation(String ram, String rom, String color) throws ProductException {
        return null;
    }

    @Override
    public Page<Product> getAllProducts(String firstLevel, String secondLevel, String thirdLevel,
                                        List<String> ram, List<String> rom, String os,
                                        Integer minBattery, Integer maxBattery, Float minScreenSize, Float maxScreenSize,
                                        Long minPrice, Long maxPrice, Integer minDiscount, String sort,
                                        Integer pageNumber, Integer pageSize) {

        pageNumber = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;
        pageSize = (pageSize != null && pageSize > 0) ? pageSize : 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.filterProducts(
                firstLevel != null && !firstLevel.trim().isEmpty() ? firstLevel : null,
                secondLevel != null && !secondLevel.trim().isEmpty() ? secondLevel : null,
                thirdLevel != null && !thirdLevel.trim().isEmpty() ? thirdLevel : null,
                ram != null && !ram.isEmpty() ? ram : null,
                rom != null && !rom.isEmpty() ? rom : null,
                os != null && !os.trim().isEmpty() ? os : null,
                minBattery,
                maxBattery,
                minScreenSize,
                maxScreenSize,
                minPrice,
                maxPrice,
                minDiscount,
                sort,
                pageable
        );
    }

    @Override
    public List<String> getAllThirdLevels() {
        return productRepository.findDistinctThirdLevels();
    }

    @Override
    public List<String> getAllSecondLevels() {
        return productRepository.findDistinctSecondLevels();
    }

    @Override
    public List<String> getThirdLevels(String secondLevel) {
        return productRepository.findThirdLevelsBySecondLevel(secondLevel);
    }

    @Override
    public Page<Product> filterByPriceBatteryAndScreenSize(
            String keyword,
            Integer minBattery, Integer maxBattery,
            Float minScreenSize, Float maxScreenSize,
            Long minPrice, Long maxPrice, String secondLevel, String thirdLevel,
            String sort, Integer pageNumber, Integer pageSize) {

        // Xử lý giá trị mặc định cho phân trang
        pageNumber = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;
        pageSize = (pageSize != null && pageSize > 0) ? pageSize : 10;

        // Tạo Pageable với sort
        Sort sortObj = Sort.unsorted();
        if (sort != null && !sort.trim().isEmpty()) {
            switch (sort.toLowerCase()) {
                case "price_asc":
                    sortObj = Sort.by(Sort.Order.asc("variations.discountPrice"));
                    break;
                case "price_desc":
                    sortObj = Sort.by(Sort.Order.desc("variations.discountPrice"));
                    break;
                case "newest":
                    sortObj = Sort.by(Sort.Order.desc("createAt"));
                    break;
                default:
                    break;
            }
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortObj);

        // Sử dụng Specification để lọc
        Specification<Product> spec = ProductSpecification.filterByKeywordPriceBatteryAndScreenSize(
                keyword, minBattery, maxBattery,
                minScreenSize, maxScreenSize,
                minPrice, maxPrice, secondLevel, thirdLevel
        );

        return productRepository.findAll(spec, pageable);
    }
}
