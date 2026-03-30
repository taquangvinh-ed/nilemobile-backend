package com.nilemobile.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequest {
    private String productName;

    private Map<String, String> attributes;

    private Long categoryId;
}
