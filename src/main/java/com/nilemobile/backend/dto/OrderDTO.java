package com.nilemobile.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private long totalPrice;
    private long totalDiscountPrice;
    private int totalItem;
    private String status;
    private Long userId;
    private AddressDTO shippingAddress;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
    }

    public Long getId() {
        return orderId;
    }

    public void setId(Long id) {
        this.orderId = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(long totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AddressDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
