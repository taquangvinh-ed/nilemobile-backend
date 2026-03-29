package com.nilemobile.backend.service;

import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

public interface JwtTokenService {
    String generateToken(Authentication authentication);

    SecretKey getJwtSecretKey();
}
