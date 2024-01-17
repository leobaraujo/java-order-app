package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.repository.OrderRepository;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(UUID id) throws NoSuchElementException {
        return orderRepository.findById(id).orElseThrow();
    }

}
