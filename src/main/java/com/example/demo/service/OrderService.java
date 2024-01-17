package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Order;

@Service
public interface OrderService {

    public List<Order> getAll();

    public Order getById(UUID id) throws NoSuchElementException;

}
