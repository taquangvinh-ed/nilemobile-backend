package com.nilemobile.backend.filter;

import com.nilemobile.backend.config.CustomUserDetails;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.ExpiredJwtException;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidationFiler extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            SecretKey key = jwtTokenService.getJwtSecretKey();

            try {
                Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
                
                Long userId = claims.get("userId", Long.class);
                List<String> authorities = claims.get("authorities", List.class);
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .map(GrantedAuthority.class::cast)
                        .collect(Collectors.toList());

                User user = new User();
                user.setUserId(userId);
                CustomUserDetails userDetails = new CustomUserDetails(user);
                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities)
                );
                
            } catch (ExpiredJwtException e) {
                SecurityContextHolder.clearContext();
                throw new ExpiredJwtException(ErrorCode.JWT_EXPIRED.getMessage());
            } catch (JwtException e) {
                log.warn("JWT signature validation failed or invalid: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            } catch (Exception e) {
                log.error("JWT validation error: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
