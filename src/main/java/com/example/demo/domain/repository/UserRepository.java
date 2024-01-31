package com.example.demo.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findOneByUsername(String username);

}
