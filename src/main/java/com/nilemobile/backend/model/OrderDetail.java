package com.nilemobile.backend.model;

import com.nilemobile.backend.contant.DiscountType;
import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    private Long price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Long discountPrice;

    private Long discountPercentage;

    private Long finalPrice;

    @Column(name = "totalDiscountPrice", nullable = false)
    private Long totalDiscountPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;



}
