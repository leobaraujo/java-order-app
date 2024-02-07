package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Profile("dev")
@Configuration
@EnableWebSecurity
public class DevSecurityConfig {

    private static final String[] WHITELIST_PATH = {
            "/api/auth"
    };

    private static final String[] ANY_ROLE_PATH = {
            "/api/customer/**",
            "/api/product/**",
            "/api/order/**",
            "/api/partialPayment/**"
    };

    private static final String[] ADMIN_PATH = {
            "/api/product/**",
            "/api/user/**",
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(WHITELIST_PATH).permitAll()
                            .requestMatchers(ANY_ROLE_PATH).authenticated()
                            .requestMatchers(ADMIN_PATH).hasRole("ADMIN");
                })
                .build();
    }

}
