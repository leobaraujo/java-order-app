package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.api.dto.NewUserDTO;
import com.example.demo.domain.entity.User;

public interface UserService {

    public List<User> getAll();

    public User getById(UUID id) throws NoSuchElementException;

    public User create(NewUserDTO newUserDTO) throws Exception;

    public void update(UUID id, NewUserDTO newUserDTO) throws Exception;

    public void updateRole(UUID id, String newRole) throws Exception;

}
