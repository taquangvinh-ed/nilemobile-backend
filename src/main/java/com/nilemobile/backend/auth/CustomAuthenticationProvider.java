package com.nilemobile.backend.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.lang.Nullable;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String identifier = authentication.getName();
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("Password is required");
        }
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = customUserDetailService.loadUserByUsername(identifier);

        if (userDetails == null)
            throw new RuntimeException("User with " + identifier + " not found");

        String storedPassword = userDetails.getPassword();
        System.out.println("StoredPassword: " + storedPassword);
        if (!passwordEncoder.matches(password, storedPassword))
            throw new BadCredentialsException("Password is incorrect!");
        else return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
