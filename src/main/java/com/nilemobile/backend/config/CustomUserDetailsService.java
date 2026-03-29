package com.nilemobile.backend.config;

import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.UserNotExistedException;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(input)
                .orElseGet(() -> userRepository.findByPhoneNumber(input)
                        .orElseThrow(() -> new UserNotExistedException(ErrorCode.USER_NOT_FOUND.getMessage())));


        return new CustomUserDetails(user);
    }
}
