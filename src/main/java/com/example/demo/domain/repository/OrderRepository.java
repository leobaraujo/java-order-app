package com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    public Optional<Order> findByProductIdAndCustomerId(UUID productId, UUID customerId);

    public void deleteAllByCustomerId(UUID customerId);

    public List<Order> findAllByCustomerId(UUID customerId);

}
