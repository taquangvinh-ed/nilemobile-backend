package com.nilemobile.backend.dto;

import com.nilemobile.backend.contant.OrderStatus;

public class OrderStatusDTO {
    private OrderStatus status;

    public OrderStatusDTO() {
    }

    public OrderStatusDTO(OrderStatus status) {
        this.status = status;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
