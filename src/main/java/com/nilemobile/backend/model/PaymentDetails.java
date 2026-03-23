package com.nilemobile.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment_details")
public class PaymentDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentDetailsId;

    @OneToOne(mappedBy = "paymentDetails")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private String paymentId;

    private String paymentUrl;

    private String transactionRef;

    private String transactionNo;

    private Long amount;

    private String responseCode;

    private LocalDateTime transactionTime;

}
