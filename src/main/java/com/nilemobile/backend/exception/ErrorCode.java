package com.nilemobile.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(1001, "User not found"),
    INVALID_CREDENTIALS(2001, "Invalid email or password"),
    EMAIL_ALREADY_EXISTS(1002, "Email already exists"),
    PHONE_NUMBER_ALREADY_EXISTS(1003, "Phone number already exists"),
    INTERNAL_SERVER_ERROR(5000, "An unexpected error occurred");

    private final int code;
    private final String message;


}
