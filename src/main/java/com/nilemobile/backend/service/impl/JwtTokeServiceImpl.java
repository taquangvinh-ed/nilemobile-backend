package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.config.CustomUserDetails;
import com.nilemobile.backend.service.JwtTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        CustomUserDetails userDatails = (CustomUserDetails) authentication.getPrincipal();

        SecretKey secretKey = getJwtSecretKey();
        String jwtToken = Jwts.builder()
                .issuer("nilemobile")
                .claim("userId", userDatails.getUserId())
                .claim("authorities", userDatails.getAuthorities())
                .setSubject(userDatails.getUsername())
                .signWith(secretKey)
                .compact();
        return jwtToken;

    }

    @Override
    public SecretKey getJwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

}
