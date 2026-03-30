package com.nilemobile.backend.dto;

import java.util.List;

public class AdminCartDTO {
    private Long id;
    private Long subtotal;
    private Long totalDiscountPrice;
    private int totalDiscountPercent;
    private int totalItems;
    private List<AdminCartItemDTO> cartItems;

    public AdminCartDTO(Long id, Long subtotal, Long totalDiscountPrice, int totalDiscountPercent, int totalItems, List<AdminCartItemDTO> cartItems) {
        this.id = id;
        this.subtotal = subtotal;
        this.totalDiscountPrice = totalDiscountPrice;
        this.totalDiscountPercent = totalDiscountPercent;
        this.totalItems = totalItems;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(int totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<AdminCartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<AdminCartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
