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

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    private Long totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "addressId")
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
