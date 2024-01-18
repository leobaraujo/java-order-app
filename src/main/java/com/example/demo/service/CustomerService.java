package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.CustomerStatus;

public interface CustomerService {

    public List<Customer> getAll();

    public Customer getById(UUID id) throws NoSuchElementException;

    public CustomerStatus updateStatus(UUID id) throws NoSuchElementException;

}
