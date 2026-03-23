package com.nilemobile.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetail_id", length = 36)
    private Long orderDetailId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "subtotal", nullable = false)
    private Long subtotal;

    @Column(name = "totalDiscountPrice", nullable = false)
    private Long totalDiscountPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    @ManyToOne
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;


}
