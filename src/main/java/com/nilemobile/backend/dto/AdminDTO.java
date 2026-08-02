package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO {
    private Long adminId;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String createdDateAt;
}
