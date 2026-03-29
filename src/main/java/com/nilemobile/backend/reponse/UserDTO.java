package com.nilemobile.backend.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long userId;

    private String email;

    private String password;

    private String phoneNumber;

    private String role;

    private String createdDateAt;
}
