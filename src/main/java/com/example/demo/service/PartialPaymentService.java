package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.domain.entity.PartialPayment;

public interface PartialPaymentService {

    public List<PartialPayment> getAll();

    public PartialPayment getById(UUID id) throws NoSuchElementException;

    public List<PartialPayment> getAllByCustomerId(UUID id) throws NoSuchElementException;

}
