package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Map<String, String> attributes;
    private String categoryName;
    private List<VariationDTO> variations;
}
