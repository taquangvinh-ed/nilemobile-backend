package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserDTO;
import com.nilemobile.backend.request.CreateNewUserRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    User registerUser(CreateNewUserRequest request) throws UserException;

    String login(HttpServletRequest request, String identifier, String password);

    UserDTO findUserById(Long userId) throws UserException;
}
