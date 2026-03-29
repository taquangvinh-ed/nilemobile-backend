package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Float screenSize;
    private Integer batteryCapacity;
    private String os;
    private String categoryName;
    private Long minPrice;
    private Integer stockQuantity;
    private String ram;
    private String rom;
    private List<VariationDTO> variations;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.screenSize = product.getScreenSize();
        this.batteryCapacity = product.getBatteryCapacity();
        this.os = product.getOs();
        this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        this.minPrice = product.getVariations().stream()
                .mapToLong(Variation::getPrice).min().orElse(0L);
        this.stockQuantity = product.getVariations().stream()
                .mapToInt(Variation::getStockQuantity).sum();
        this.ram = product.getVariations().isEmpty() ? null : product.getVariations().get(0).getRam();
        this.rom = product.getVariations().isEmpty() ? null : product.getVariations().get(0).getRom();

        this.variations = product.getVariations().stream()
                .map(VariationDTO::new)
                .collect(Collectors.toList());
    }

    public List<VariationDTO> getVariations() {
        return variations;
    }

    public void setVariations(List<VariationDTO> variations) {
        this.variations = variations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Float screenSize) {
        this.screenSize = screenSize;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }
}
