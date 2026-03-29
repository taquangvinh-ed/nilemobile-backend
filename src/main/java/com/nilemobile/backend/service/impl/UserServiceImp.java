package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.*;
import com.nilemobile.backend.mapper.UserMapper;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserDTO;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.request.CreateNewUserRequest;
import com.nilemobile.backend.service.AuthService;
import com.nilemobile.backend.service.JwtTokenService;
import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;

    @Override
    @Transactional
    public User registerUser(CreateNewUserRequest request) throws UserException {

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getPhoneNumber());
        if (existingUserByEmail.isPresent()) {
            throw new EmailAlreadyExisted(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        Optional<User> existingUserByPhoneNumber = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingUserByPhoneNumber.isPresent()) {
            throw new PhoneNumberAlreadyExisted(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getMessage());
        }

        // Map request to User entity (password ignored in mapper)
        User newUser = userMapper.toEntity(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public String login(HttpServletRequest request, String identifier, String password) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(identifier, password);
            authentication = authenticationManager.authenticate(authentication);
            authService.setContextHolder( request, authentication);
            return jwtTokenService.generateToken(authentication);
    }

    @Override
    public UserDTO findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        }
        throw new UserNotExistedException(ErrorCode.USER_NOT_FOUND.getMessage());
    }







}
