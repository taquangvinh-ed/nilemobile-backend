package com.nilemobile.backend.config;

import com.nilemobile.backend.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

    /**
     * Bean để Spring Data JPA tự động lấy current user khi save entity
     * Nếu không có authentication, sẽ return empty (có thể set null hoặc default)
     */
    @Bean
    public AuditorAware<User> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                
                // Nếu principal là User entity, return nó
                if (principal instanceof User) {
                    return Optional.of((User) principal);
                }
                // Nếu principal là String (username), có thể load user từ DB
                // (implementation tùy cách bạn store authentication)
            }
            
            return Optional.empty();
        };
    }
}

