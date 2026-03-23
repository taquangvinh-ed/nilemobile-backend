package com.nilemobile.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "addresses")
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "firstName", length = 50, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", length = 11, nullable = false)
    private String phoneNumber;

    @Column(name = "addressDetail", length = 150, nullable = false)
    private String addressLine;

    @Column(name = "ward", length = 30, nullable = false)
    private String ward;

    @Column(name = "district", length = 30, nullable = false)
    private String district;

    @Column(name = "province", length = 30, nullable = false)
    private String province;

    @Column(name = "isDefault", nullable = false)
    private Boolean isDefault = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;
}
