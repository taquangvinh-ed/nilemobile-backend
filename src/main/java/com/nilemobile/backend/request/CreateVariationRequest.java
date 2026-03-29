package com.nilemobile.backend.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class dtcuCreateVariationRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotBlank(message = "Color cannot be blank")
    private String color;

    @NotBlank(message = "RAM cannot be blank")
    private String ram;

    @NotBlank(message = "ROM cannot be blank")
    private String rom;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be non-negative")
    private Long price;

    @Min(value = 0, message = "Discount price must be non-negative")
    private Long discountPrice;

    @Min(value = 0, message = "Discount percent must be non-negative")
    private int discountPercent;

    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity;

    private String imageURL;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}