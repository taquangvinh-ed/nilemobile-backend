package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.request.CreateNewUserRequest;

public interface AuthenticationService {
    void register(CreateNewUserRequest request);
    String login(String identifier, String password);
}
