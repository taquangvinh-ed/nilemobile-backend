package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.*;
import com.nilemobile.backend.mapper.UserMapper;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.dto.reponse.UserDTO;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.service.JwtTokenService;
import com.nilemobile.backend.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional
    public User registerUser(CreateNewUserRequest request) throws UserException {

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new EmailAlreadyExisted(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        Optional<User> existingUserByPhoneNumber = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingUserByPhoneNumber.isPresent()) {
            throw new PhoneNumberAlreadyExisted(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getMessage());
        }
        User newUser = userMapper.toEntity(request);
        newUser.setPwdHash(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(newUser);
    }


    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String token = jwt != null && jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
        Claims claims = jwtTokenService.extractClaims(token);
        Number userId = claims.get("userId", Number.class);
        if (userId == null) {
            throw new UserNotExistedException(ErrorCode.USER_NOT_FOUND.getMessage());
        }
        return userRepository.findById(userId.longValue())
                .orElseThrow(() -> new UserNotExistedException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistedException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }


}
