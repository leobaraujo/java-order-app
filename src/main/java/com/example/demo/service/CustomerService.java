package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.CustomerStatus;

@Service
public interface CustomerService {

    public List<Customer> getAll();

    public Customer getById(UUID id) throws Exception;

    public CustomerStatus updateStatus(UUID id) throws Exception;

}
