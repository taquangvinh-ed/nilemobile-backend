package com.nilemobile.backend.dto;

public class AdminCartItemDTO {
    private Long id;
    private Long variationId;
    private String variationName;
    private String imageURL;
    private int quantity;
    private Long subtotal;
    private Long totalDiscountPrice;

    public AdminCartItemDTO(Long id, Long variationId, String variationName, String imageURL, int quantity, Long subtotal, Long totalDiscountPrice) {
        this.id = id;
        this.variationId = variationId;
        this.variationName = variationName;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(Long totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }
}
