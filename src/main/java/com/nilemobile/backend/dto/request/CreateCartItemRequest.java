package com.nilemobile.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartItemRequest {
    private Long variationId;
    private Long cartId;
    private Integer quantity;
}
