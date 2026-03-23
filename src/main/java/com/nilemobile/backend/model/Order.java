package com.nilemobile.backend.model;

import com.nilemobile.backend.contant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "TotalPrice", nullable = false)
    private Long totalPrice;

    @Column(name = "TotalDiscountPrice", nullable = false)
    private Long totalDiscountPrice;

    @ManyToOne
    @JoinColumn(name = "U_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToOne
    @JoinColumn(name = "paymentDetail_id", referencedColumnName = "id")
    private PaymentDetails paymentDetails;

    @ManyToOne
    @JoinColumn(name = "addressId", referencedColumnName = "address_id")
    private Address shippingAddress;

    private int totalItem;

    @Column(name = "Status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
