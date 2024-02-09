package com.example.demo.config;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtDecoder {

    private JwtProperties jwtProperties;

    public JwtDecoder(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public DecodedJWT decode(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey())).build().verify(token);
        } catch(Exception e) {
            return null;
        }
    }

}
