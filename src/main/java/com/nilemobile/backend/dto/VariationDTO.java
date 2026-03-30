package com.nilemobile.backend.dto;

import com.nilemobile.backend.contant.DiscountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class VariationDTO {

    private String variationName;

    private Map<String, String> attributes;

    private int stockQuantity;

    private Long productId;

    private DiscountType discountType;

    private Long discountPrice;

    private Long discountPercentage;

    private Long finalPrice;

}
