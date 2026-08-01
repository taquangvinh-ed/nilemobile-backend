package com.nilemobile.backend.config;

import com.nilemobile.backend.auth.CustomUserDetail;
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
     * Bean để Spring Data JPA tự động lấy current user id khi save entity
     * Nếu không có authentication, sẽ return empty (có thể set null hoặc default)
     */
    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                // Principal được set bởi JwtTokenValidateFilter là CustomUserDetail
                if (principal instanceof CustomUserDetail customUserDetail) {
                    return Optional.ofNullable(customUserDetail.getUserId());
                }
                // Fallback nếu principal là User entity
                if (principal instanceof User user) {
                    return Optional.ofNullable(user.getUserId());
                }
            }

            return Optional.empty();
        };
    }
}
