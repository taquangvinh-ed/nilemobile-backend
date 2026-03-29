package com.nilemobile.backend.service;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserDTO;
import com.nilemobile.backend.request.ChangePasswordRequest;
import com.nilemobile.backend.request.CreateNewUserRequest;

public interface UserService {

    User registerUser(CreateNewUserRequest request) throws UserException;

    String login(String identifier, String password);

    UserDTO findUserById(Long userId) throws UserException;
}
