package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.config.CustomUserDetails;
import com.nilemobile.backend.config.CustomUserDetailsService;
import com.nilemobile.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService customUserDetailsService;


    @Override
    public void setContextHolder(HttpServletRequest request, Authentication authentication) {

        if(authentication instanceof UsernamePasswordAuthenticationToken authToken) {
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
