package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
    private Long id;

    private String name;

    VariationDTO variation;

    private Integer quantity;

    private long subtotal = 0L;

    private Long discountPrice;

    private Boolean isSelected;

}
