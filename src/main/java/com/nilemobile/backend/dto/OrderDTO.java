package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private long totalPrice;
    private int totalItem;
    private String status;
    private Map<String, String> customerInfo;
    private AddressDTO shippingAddress;
    private List<OrderDetailDTO> orderDetails;
 }

