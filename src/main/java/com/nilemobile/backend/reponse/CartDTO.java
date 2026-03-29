package com.nilemobile.backend.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
    private long subtotal;
    private Long totalDiscountPrice;
    private int totalDiscountPercent;
    private int totalItems;
    private List<CartItemDTO> cartItems;
}