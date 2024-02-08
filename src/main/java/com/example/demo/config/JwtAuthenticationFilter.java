package com.example.demo.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtDecoder jwtDecoder;
    private JwtToUserPrincipal jwtToUserPrincipal;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder, JwtToUserPrincipal jwtToUserPrincipal) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToUserPrincipal = jwtToUserPrincipal;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromHeader(request)
                .map(jwtDecoder::decode)
                .map(jwtToUserPrincipal::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }

        return Optional.empty();
    }

}
