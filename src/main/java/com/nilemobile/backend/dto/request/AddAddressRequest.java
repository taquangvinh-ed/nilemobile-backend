package com.nilemobile.backend.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddAddressRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String addressLine;

    private String ward;

    private String district;

    private String province;

}
