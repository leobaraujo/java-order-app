package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.domain.entity.Customer;

public interface CustomerService {

    public List<Customer> getAll();

    public Customer getById(UUID id) throws NoSuchElementException;

    /**
     * Toggle through possible status. When new status is {@code AVAILABLE} all
     * orders and partial payment are removed.
     * 
     * @param id
     * @throws NoSuchElementException if customer is not found
     */
    public void updateStatus(UUID id) throws NoSuchElementException;

}
