package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDTO {
    private Long orderDetailId;
    private Long productId;
    private String productName;
    private Long variationId;
    private String variationName;
    private Map<String, String> categoryInfo;
    private int quantity;
    private long subtotal;
}
