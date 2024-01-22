package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.PartialPayment;
import com.example.demo.domain.repository.PartialPaymentRepository;
import com.example.demo.service.PartialPaymentService;

@Service
public class PartialPaymentServiceImp implements PartialPaymentService {

    private PartialPaymentRepository partialPaymentRepository;

    public PartialPaymentServiceImp(PartialPaymentRepository partialPaymentRepository) {
        this.partialPaymentRepository = partialPaymentRepository;
    }

    @Override
    public List<PartialPayment> getAll() {
        return partialPaymentRepository.findAll();
    }

}
