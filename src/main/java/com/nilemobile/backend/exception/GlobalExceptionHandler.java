package com.nilemobile.backend.exception;

import com.nilemobile.backend.reponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PhoneNumberAlreadyExisted.class)
    public ResponseEntity<ApiResponse> handleUserByPhoneNumberAlreadyExisted(PhoneNumberAlreadyExisted ex) {

        ErrorCode e = ErrorCode.PHONE_NUMBER_ALREADY_EXISTS;

        ApiResponse response = ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmailAlreadyExisted.class)
    public ResponseEntity<ApiResponse> handleUserByEmailAlreadyExisted(PhoneNumberAlreadyExisted ex) {
        ErrorCode e = ErrorCode.EMAIL_ALREADY_EXISTS;
        ApiResponse response = ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
