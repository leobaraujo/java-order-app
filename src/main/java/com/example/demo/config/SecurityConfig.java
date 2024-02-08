package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] WHITELIST_PATH = {
            "/api/auth"
    };

    private static final String[] WORKER_PATH = {
            "/api/customer/**",
            "/api/product/**",
            "/api/order/**",
            "/api/partialPayment/**"
    };

    private static final String[] ADMIN_PATH = {
            "/api/user/**",
    };

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(WHITELIST_PATH).permitAll()
                            .requestMatchers(WORKER_PATH).hasAnyAuthority("WORKER", "ADMIN")
                            .requestMatchers(ADMIN_PATH).hasAuthority("ADMIN");
                })
                .build();
    }

}
