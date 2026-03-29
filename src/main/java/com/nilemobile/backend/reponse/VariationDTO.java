package com.nilemobile.backend.reponse;

import com.nilemobile.backend.contant.DiscountType;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Review;
import com.nilemobile.backend.model.Variation;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class VariationDTO {
    private Long variationId;

    private Map<String, String> attributes;

    private int stockQuantity;

    private Long productId;

    private DiscountType discountType;

    private Long discountPrice;

    private Long discountPercentage;

    private Long finalPrice;

}
