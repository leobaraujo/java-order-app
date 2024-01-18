package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.api.dto.NewOrderDTO;
import com.example.demo.api.dto.UpdateOrderDTO;
import com.example.demo.domain.entity.Order;
import com.example.demo.domain.exception.InvalidEntityIdException;

public interface OrderService {

    public List<Order> getAll();

    public Order getById(UUID id) throws NoSuchElementException;

    public List<Order> getAllByCustomerId(UUID id);

    public Order createOrder(NewOrderDTO newOrder) throws InvalidEntityIdException;

    public void updateOrder(UUID id, UpdateOrderDTO updateOrderDTO) throws NoSuchElementException;

    public void deleteOrderById(UUID id);

}
