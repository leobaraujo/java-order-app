package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.api.dto.NewUserDTO;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.entity.UserRoles;
import com.example.demo.domain.exception.InvalidUsernameException;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(UUID id) throws NoSuchElementException {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public User create(NewUserDTO newUserDTO) throws Exception {
        Optional<User> userDb = userRepository.findOneByUsername(newUserDTO.username());
        
        if (userDb.isPresent()) {
            throw new InvalidUsernameException("Invalid username.");
        }

        var newUser = new User();
        String hashedPassword = new BCryptPasswordEncoder().encode(newUserDTO.password());

        newUser.setUsername(newUserDTO.username());
        newUser.setPassword(hashedPassword);
        newUser.setRole(UserRoles.WORKER.toString());

        return userRepository.save(newUser);
    }

}
