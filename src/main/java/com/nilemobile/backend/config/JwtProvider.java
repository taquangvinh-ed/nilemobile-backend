package com.nilemobile.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtProvider {
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 846000000)).claim("phoneNumber", authentication.getName()).signWith(key).compact();
        return jwt;
    }

    public String getPhoneNumberfromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String phoneNumber = String.valueOf(claims.get("phoneNumber"));
        return phoneNumber;
    }

}
