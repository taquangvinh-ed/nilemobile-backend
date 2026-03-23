package com.nilemobile.backend.service;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.RegisterResponseDTO;
import com.nilemobile.backend.reponse.UserProfileDTO;
import com.nilemobile.backend.request.ChangePasswordRequest;
import com.nilemobile.backend.request.CreateUserRequest;

public interface UserService {

    RegisterResponseDTO registerUser(CreateUserRequest request) throws UserException;

    public User findUserById(Long userID) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public UserProfileDTO updateProfile(Long userId, User user);

    public User updateProfileUser(Long userId, UserProfileDTO userProfileDTO) throws UserException;

    public User findByPhoneNumber(String phoneNumber);

    void changePassword(String phoneNumber, ChangePasswordRequest request) throws Exception;
}
