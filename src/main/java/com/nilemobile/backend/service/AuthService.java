package com.nilemobile.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void setContextHolder(HttpServletRequest request, Authentication authentication);
}
