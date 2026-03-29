package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.ApiResponse;
import com.nilemobile.backend.reponse.RegisterNewCustomerResponseDTO;
import com.nilemobile.backend.reponse.UserDTO;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.request.ChangePasswordRequest;
import com.nilemobile.backend.request.CreateNewUserRequest;
import com.nilemobile.backend.service.UserException;
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
    private final UserRepository userRepository;


    @PostMapping("/register")
    ApiResponse<RegisterNewCustomerResponseDTO> register(@Validated @RequestBody CreateNewUserRequest request) {
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .code(200)
                .timestamp(Timestamp.from(Instant.now()))
                .body(userService.registerUser(request))
                .build();
        return response;
    }

    @GetMapping("/me")
    public ApiResponse<UserDTO> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long user
        User user = userService.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new UserException("Không tìm thấy thông tin của bạn");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setLastName(user.getLastName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDTO userDTO = new UserDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedDateAt().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))
        );
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long userId, @RequestBody User user) throws UserException {
        UserDTO updatedUserProfile = userService.updateProfile(userId, user);
        return ResponseEntity.ok(updatedUserProfile);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        List<User> users = userRepository.findAll();
        List<UserDTO> userProfiles = users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getCreatedDateAt().format(formatter)))
                .toList();
        return ResponseEntity.ok(userProfiles);
    }

    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserDTO userDTO) throws UserException {
        User updatedUser = userService.updateProfileUser(userId, userDTO);
        return ResponseEntity.ok(new UserDTO(updatedUser));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {
        try {
            // Lấy phoneNumber từ Authentication (được thiết lập bởi JwtValidator)
            String phoneNumber = authentication.getName();
            userService.changePassword(phoneNumber, request);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
