package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.dto.RegisterNewCustomerResponseDTO;
import com.nilemobile.backend.dto.reponse.UserDTO;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.dto.request.ChangePasswordRequest;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ApiResponse<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
       return  ApiResponse.builder()
                .success(true)
                .code(200)
                .message("Get all users successfully")
                .timestamp(Timestamp.from(Instant.now()))
                .body(users)
                .build();
    }}
