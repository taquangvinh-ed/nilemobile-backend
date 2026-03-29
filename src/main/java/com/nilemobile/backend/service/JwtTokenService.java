package com.nilemobile.backend.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

public interface JwtTokenService {
    String generateToken(Authentication authentication);

    SecretKey getJwtSecretKey();

    boolean validateToken(String token);

    Claims extractClaims(String token);
}
