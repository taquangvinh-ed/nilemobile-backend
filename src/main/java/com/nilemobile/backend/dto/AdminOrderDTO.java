package com.nilemobile.backend.dto;

import java.util.List;

public class AdminOrderDTO {
    private Long orderId;
    private String fullname;
    private String phoneNumber;
    private Long totalPrice;
    private String status;
    private String orderDate;
    private String shippingAddress;
    private String paymentMethod;
    private List<OrderDetailDTO> orderDetails;

    public AdminOrderDTO() {
    }

    public AdminOrderDTO(Long orderId, String fullname, String phoneNumber, Long totalPrice, String status, String orderDate, String shippingAddress, String paymentMethod, List<OrderDetailDTO> orderDetails) {
        this.orderId = orderId;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderDetails = orderDetails;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
