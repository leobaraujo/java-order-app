package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.CustomerStatus;
import com.example.demo.domain.repository.CustomerRepository;
import com.example.demo.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImp implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(UUID id) throws NoSuchElementException {
        return customerRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public CustomerStatus updateStatus(UUID id) throws NoSuchElementException {
        Customer customer = getById(id);
        CustomerStatus newStatus = CustomerStatus.AVAILABLE;

        for (int i = 0; i < CustomerStatus.values().length; i++) {
            if (CustomerStatus.values()[i].toString().equals(customer.getStatus())) {
                newStatus = i + 1 < CustomerStatus.values().length ? CustomerStatus.values()[i + 1] : CustomerStatus.AVAILABLE;
                break;
            }
        }

        customer.setStatus(newStatus.toString());
        customerRepository.save(customer);

        return newStatus;
    }

}
