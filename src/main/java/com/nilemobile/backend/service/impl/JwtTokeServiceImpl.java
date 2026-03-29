package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.config.CustomUserDetails;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.InvalidTokenException;
import com.nilemobile.backend.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtTokeServiceImpl implements JwtTokenService {

    private String jwtSecretKey;

    public JwtTokeServiceImpl(@Value("${jwt.sercet-key}") String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    public String generateToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        SecretKey secretKey = getJwtSecretKey();
        String jwtToken = Jwts.builder()
                .issuer("nilemobile")
                .claim("userId", userDetails.getUserId())
                .claim("authorities", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .signWith(secretKey)
                .compact();


        return jwtToken;

    }

    @Override
    public SecretKey getJwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getJwtSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException
                 | ExpiredJwtException
                 | UnsupportedJwtException
                 | IllegalArgumentException e) {
            throw new InvalidTokenException(ErrorCode.INVALID_JWT.getMessage());
        }
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(getJwtSecretKey()).build().parseSignedClaims(token).getPayload();
    }

}
