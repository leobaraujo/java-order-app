package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.NewPartialPaymentDTO;
import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.CustomerStatus;
import com.example.demo.domain.entity.PartialPayment;
import com.example.demo.domain.repository.PartialPaymentRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.PartialPaymentService;

import jakarta.transaction.Transactional;

@Service
public class PartialPaymentServiceImp implements PartialPaymentService {

    private PartialPaymentRepository partialPaymentRepository;
    private CustomerService customerService;

    public PartialPaymentServiceImp(PartialPaymentRepository partialPaymentRepository,
            CustomerService customerService) {
        this.partialPaymentRepository = partialPaymentRepository;
        this.customerService = customerService;
    }

    @Override
    public List<PartialPayment> getAll() {
        return partialPaymentRepository.findAll();
    }

    @Override
    public PartialPayment getById(UUID id) throws NoSuchElementException {
        return partialPaymentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<PartialPayment> getAllByCustomerId(UUID id) throws NoSuchElementException {
        return partialPaymentRepository.findAllByCustomerId(id);
    }

    @Override
    @Transactional
    public PartialPayment createPartialPayment(NewPartialPaymentDTO newPartialPaymentDTO) throws IllegalArgumentException {
        if (newPartialPaymentDTO.amount() < 0.0) {
            throw new IllegalArgumentException("Amount value cannot be less than zero.");
        }

        try {
            Customer customer = customerService.getById(UUID.fromString(newPartialPaymentDTO.customerId()));
            
            if (!customer.getStatus().equals(CustomerStatus.BUSY.toString())) {
                throw new IllegalArgumentException("Customer status is not BUSY.");
            }

            PartialPayment partialPayment = new PartialPayment();
            partialPayment.setCustomer(customer);
            partialPayment.setAmount(newPartialPaymentDTO.amount());
            partialPayment.setNote(newPartialPaymentDTO.note());

            return partialPaymentRepository.save(partialPayment);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format.");
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Customer not found.");
        }
    }

}
