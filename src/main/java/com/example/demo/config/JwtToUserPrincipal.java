package com.example.demo.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;

@Component
public class JwtToUserPrincipal {

    private UserRepository userRepository;

    public JwtToUserPrincipal(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPrincipal convert(DecodedJWT token) {
        UUID userId = UUID.fromString(token.getSubject());
        Optional<User> userOpt = userRepository.findById(userId);

        if (!userOpt.isPresent()) {
            return null;
        }

        return new UserPrincipal(userId, AuthorityUtils.createAuthorityList(userOpt.get().getRole()));
    }

}
