package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.domain.entity.User;

public interface UserService {

    public List<User> getAll();

    public User getById(UUID id) throws NoSuchElementException;

}
