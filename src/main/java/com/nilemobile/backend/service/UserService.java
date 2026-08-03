package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.dto.reponse.UserDTO;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import java.util.List;

public interface UserService {

    User registerUser(CreateNewUserRequest request);

    User findUserProfileByJwt(String jwt) throws UserException;

    User findUserById(Long userId) throws UserException;

    List<UserDTO> getAllUsers();

    User getUserById(Long userId);
}
