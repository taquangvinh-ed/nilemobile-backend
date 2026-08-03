package com.nilemobile.backend.auth;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String input) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(input)
                .or(() -> userRepository.findByEmail(input))
                .or(() -> userRepository.findByUsername(input))
                .orElseThrow(() -> new RuntimeException("User not found with username or email: " + input));
        return new CustomUserDetail(user);

    }
}
