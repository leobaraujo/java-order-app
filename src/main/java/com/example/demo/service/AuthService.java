package com.example.demo.service;

import com.example.demo.domain.exception.InvalidUserException;

public interface AuthService {

    /**
     * Checks if the user exists by username and then compare passwords.
     * 
     * @param username
     * @param password
     * @return user {@code id}
     * @throws InvalidUserException if user is not found or passwords doesn't match.
     */
    public String validateCredentials(String username, String password) throws InvalidUserException;

    /**
     * Generates a JWT with users id as {@code subject}.
     * 
     * @param userId
     * @return JWT encrypted with HMAC256 algorithm that expires in 12h.
     */
    public String generateJwt(String userId);

}
