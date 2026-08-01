package com.nilemobile.backend.dto.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long userId;

    private String email;

    private String password;

    private String phoneNumber;

    private List<String> roleName;

    private String createdDateAt;
}
