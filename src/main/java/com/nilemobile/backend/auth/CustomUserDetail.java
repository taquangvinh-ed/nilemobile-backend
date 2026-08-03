package com.nilemobile.backend.auth;


import com.nilemobile.backend.model.User;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetail implements UserDetails {

    private final User user;
    private final Long userId;
    private final String username;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetail(User user) {
        this.user = user;
        this.userId = null;
        this.username = null;
        this.authorities = null;
    }

    private CustomUserDetail(Long userId, String username, List<GrantedAuthority> authorities) {
        this.user = null;
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
    }

    public static CustomUserDetail fromClaims(Claims claims) {
        Long userId = claims.get("userId", Long.class);
        String username = claims.get("name", String.class);
        List<String> authorityNames = claims.get("authorities", List.class);
        List<GrantedAuthority> authorities;
        if (authorityNames == null) {
            authorities = List.of();
        } else {
            authorities = authorityNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return new CustomUserDetail(userId, username, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));
    }

    @Override
    public @Nullable String getPassword() {
        return user == null ? null : user.getPwdHash();
    }

    @Override
    public String getUsername() {
        return user == null ? username : user.getUsername();
    }

    public String getEmail(){
        return user == null ? null : user.getEmail();
    }

    public Long getUserId(){
        return user == null ? userId : user.getUserId();
    }
}
