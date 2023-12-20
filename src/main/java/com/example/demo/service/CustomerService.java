package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Customer;

@Service
public interface CustomerService {

    public List<Customer> getAll();

}