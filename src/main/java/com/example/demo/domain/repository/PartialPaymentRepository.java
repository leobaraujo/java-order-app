package com.example.demo.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entity.PartialPayment;

public interface PartialPaymentRepository extends JpaRepository<PartialPayment, UUID> {

    public List<PartialPayment> findAllByCustomerId(UUID customerId);

    public void deleteAllByCustomerId(UUID customerId);

}
