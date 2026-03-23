package com.nilemobile.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment_information")
public class PaymentInformation extends PaymentDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentInformationId;
    private String cardholderName;

    private String cardNumber;

    private LocalDate expirationDate;

    private String cvv;

    @OneToOne
    private Order order;
}
