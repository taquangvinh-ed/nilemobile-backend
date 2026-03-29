package com.nilemobile.backend.config;

import com.nilemobile.backend.filter.JwtTokenValidationFiler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenGeneratorFilter jwtTokenGeneratorFilter;
    private final JwtTokenValidationFiler jwtTokenValidationFiler;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/products/third-levels").permitAll()
                        .requestMatchers("/api/products/second-levels").permitAll()
                        .requestMatchers("/api/products/filter").permitAll()
                        .requestMatchers("/api/products/id/{productId}").permitAll()
                        .requestMatchers("/api/user/addresses").permitAll()
                        .requestMatchers("/api/user/addresses/**").permitAll()
                        .requestMatchers("/api/variations").permitAll()
                        .requestMatchers("/api/reviews/variation/{variationId}").permitAll()
                        .requestMatchers("/api/payment-vnpay/callback").permitAll()
                        .requestMatchers("/api/payment-vnpay/verify").permitAll()
                        .requestMatchers("/api/product/getThirdLevel").permitAll()
                        .requestMatchers("/api/products/filter-simple").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()


                ).addFilterBefore(jwtTokenValidationFiler, BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(Customizer.withDefaults());
        // Vô hiệu hóa form login

        return http.build();
    }

    

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:4200"
        ));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider);
    }
}