package com.example.demo.domain.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.JwtProperties;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.exception.InvalidUserException;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImp implements AuthService {

    private UserRepository userRepository;
    private JwtProperties jwtProperties;

    public AuthServiceImp(UserRepository userRepository, JwtProperties jwtProperties) {
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String validateCredentials(String username, String password) throws InvalidUserException {
        Optional<User> userOpt = userRepository.findOneByUsername(username);

        if (!userOpt.isPresent()) {
            throw new InvalidUserException("Invalid username or password.");
        }

        User user = userOpt.get();
        boolean isValidPassword = new BCryptPasswordEncoder().matches(password, user.getPassword());

        if (!isValidPassword) {
            throw new InvalidUserException("Invalid username or password.");
        }

        return user.getId().toString();
    }

    @Override
    public String generateJwt(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Instant.now().plus(12, ChronoUnit.HOURS))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

}
