package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    private String email;

    private String phoneNumber;

    private boolean isExpired;

    private boolean isLocked;

    private boolean isEnabled;

}
