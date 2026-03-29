package com.nilemobile.backend.model;

import com.nilemobile.backend.contant.DiscountType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "variations")
public class Variation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variationId;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, String> attributes;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Long discountPrice;

    private Long discountPercentage;

    private Long finalPrice;
}
