package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.ApiResponse;
import com.nilemobile.backend.reponse.RegisterResponseDTO;
import com.nilemobile.backend.reponse.UserProfileDTO;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.request.ChangePasswordRequest;
import com.nilemobile.backend.request.CreateUserRequest;
import com.nilemobile.backend.service.UserException;
import com.nilemobile.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    ApiResponse<RegisterResponseDTO> register(@Validated @RequestBody CreateUserRequest request) {
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .code(200)
                .timestamp(Timestamp.from(Instant.now()))
                .body(userService.registerUser(request))
                .build();
        return response;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile() throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        User user = userService.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new UserException("Không tìm thấy thông tin của bạn");
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUserId(user.getUserId());
        userProfileDTO.setLastName(user.getLastName());
        userProfileDTO.setFirstName(user.getFirstName());
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setPhoneNumber(user.getPhoneNumber());

        return ResponseEntity.ok(userProfileDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long userId) throws UserException {
        User user = userService.findUserById(userId);
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedDateAt().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))
        );
        return ResponseEntity.ok(userProfileDTO);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@PathVariable Long userId, @RequestBody User user) throws UserException {
        UserProfileDTO updatedUserProfile = userService.updateProfile(userId, user);
        return ResponseEntity.ok(updatedUserProfile);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        List<User> users = userRepository.findAll();
        List<UserProfileDTO> userProfiles = users.stream()
                .map(user -> new UserProfileDTO(
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
    public ResponseEntity<UserProfileDTO> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileDTO userProfileDTO) throws UserException {
        User updatedUser = userService.updateProfileUser(userId, userProfileDTO);
        return ResponseEntity.ok(new UserProfileDTO(updatedUser));
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
