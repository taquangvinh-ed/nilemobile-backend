package com.nilemobile.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long addressId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressLine;
    private String ward;
    private String district;
    private String province;
    private Boolean isDefault;

}