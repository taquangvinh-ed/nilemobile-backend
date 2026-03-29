package com.nilemobile.backend.controller;

import com.nilemobile.backend.reponse.ApiResponse;
import com.nilemobile.backend.request.CreateNewUserRequest;
import com.nilemobile.backend.request.LoginRequest;
import com.nilemobile.backend.service.CustomerService;
import com.nilemobile.backend.service.UserService;
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

    private final CustomerService customerService;
    private final UserService userService;

    @PostMapping("/customer/signup")
    public ApiResponse<?> registerNewCustomer(@RequestBody CreateNewUserRequest request) {
        customerService.registerCustomer(request);
        String jwt = userService.login(request.getPhoneNumber(), request.getPassword());
        return ApiResponse.builder()
                .success(true)
                .code(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .timestamp(Timestamp.from(Instant.now()))
                .body(jwt)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest loginRequest) {
        String jwt = userService.login(loginRequest.getIdentifier(), loginRequest.getPassword());
        return ApiResponse.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Login successful")
                .timestamp(Timestamp.from(Instant.now()))
                .body(jwt)
                .build();

    }
}