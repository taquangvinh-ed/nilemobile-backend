package com.nilemobile.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "variations")
public class Variation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variationId;

    @Column(name = "color", length = 20)
    private String color;

    @Column(name = "RAM", length = 20)
    private String ram;

    @Column(name = "ROM", length = 20)
    private String rom;

    @Column(name = "price", length = 1000)
    private Long price;

    @Column(name = "discountPrice", length = 1000)
    private Long discountPrice;

    @Column(name = "discountPercent", length = 1000)
    private int discountPercent;

    @Column(name = "stockQuantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "imageURL", length = 1000)
    private String imageURL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<Review> reviews;


    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
