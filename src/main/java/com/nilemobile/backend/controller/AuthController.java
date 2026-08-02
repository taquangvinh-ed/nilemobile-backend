package com.nilemobile.backend.controller;

import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import com.nilemobile.backend.dto.request.LoginRequest;
import com.nilemobile.backend.service.AuthenticationService;
import com.nilemobile.backend.service.CustomerService;
import com.nilemobile.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/customer/signup")
    public ApiResponse<?> registerNewCustomer(@RequestBody CreateNewUserRequest request) {
        authenticationService.register(request);
        return ApiResponse.builder()
                .success(true)
                .code(HttpStatus.CREATED.value())
                .message("Customer registered successfully")
                .timestamp(Timestamp.from(Instant.now()))
                .body(null)
                .build();
    }

    @PostMapping("/admin/signup")
    public ApiResponse<?> registerNewAdmin(@RequestBody CreateNewUserRequest request) {
        authenticationService.register(request);
        return ApiResponse.builder()
                .success(true)
                .code(HttpStatus.CREATED.value())
                .message("Admin registered successfully")
                .timestamp(Timestamp.from(Instant.now()))
                .body(null)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest loginRequest) {
        String jwt = authenticationService.login(loginRequest.getIdentifier(), loginRequest.getPassword());
        return ApiResponse.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Login successful")
                .timestamp(Timestamp.from(Instant.now()))
                .body(jwt)
                .build();

    }
}