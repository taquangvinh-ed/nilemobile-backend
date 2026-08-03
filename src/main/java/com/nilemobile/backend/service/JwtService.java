package com.nilemobile.backend.service;


import com.nilemobile.backend.auth.CustomUserDetail;
import com.nilemobile.backend.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final String secretKey ;

    public JwtService(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        String token = Jwts.builder()
                .setSubject("Jwt Token")
                .setIssuer("Spring Security with jwt")
                .setIssuedAt(Date.from(Instant.now()))
                .claim("userId", userDetails.getUserId())
                .claim("name", authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .expiration(Date.from(Instant.now().plusSeconds(7200))) // Token valid for 1 hour
                .signWith(getSignInKey())
                .compact();
        return "Bearer " + token;
    }

    public boolean isTokenValid(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        token = stripBearerPrefix(token);
        try {
            Jwts.parser().verifyWith(getSignInKey()).build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException
                 | SignatureException
                 | ExpiredJwtException
                 | UnsupportedJwtException
                 | IllegalArgumentException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    public Map<String, Object> extractAllClaims(String token) {
        return extractClaims(token);
    }

    public String extractUsername(String token) {
        return extractClaims(token).get("name", String.class);
    }

    public Claims extractClaims(String jwt) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(stripBearerPrefix(jwt)).getPayload();
    }

    private String stripBearerPrefix(String token) {
        return token != null && token.startsWith("Bearer ") ? token.substring(7) : token;
    }

    public List extractAuthorities(String token) {
        return extractClaims(token).get("authorities", List.class);
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }
}
