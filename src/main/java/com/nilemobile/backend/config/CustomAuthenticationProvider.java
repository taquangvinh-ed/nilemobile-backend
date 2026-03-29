package com.nilemobile.backend.config;

import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.InvalidCredentalException;
import com.nilemobile.backend.exception.UserNotExistedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String identifier = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(identifier);
        if (userDetails == null) {
            throw new InvalidCredentalException(ErrorCode.INVALID_REVIEW.getMessage());
        }
        String storedPassword = userDetails.getPassword();
        if (!passwordEncoder.matches(password, storedPassword)) {

            throw new InvalidCredentalException(ErrorCode.INVALID_CREDENTIALS.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
