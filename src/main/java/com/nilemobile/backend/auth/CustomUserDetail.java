package com.nilemobile.backend.auth;


import com.nilemobile.backend.model.User;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().stream().map(role ->new SimpleGrantedAuthority("ROLE_"+role.getRoleName())).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPwdHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public Long getUserId(){
        return user.getUserId();
    }
}
