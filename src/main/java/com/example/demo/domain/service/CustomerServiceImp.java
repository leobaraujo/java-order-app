package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.CustomerStatus;
import com.example.demo.domain.repository.CustomerRepository;
import com.example.demo.domain.repository.OrderRepository;
import com.example.demo.domain.repository.PartialPaymentRepository;
import com.example.demo.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImp implements CustomerService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private PartialPaymentRepository partialPaymentRepository;

    public CustomerServiceImp(CustomerRepository customerRepository, OrderRepository orderRepository, PartialPaymentRepository partialPaymentRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.partialPaymentRepository = partialPaymentRepository;
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
    public void updateStatus(UUID id) throws NoSuchElementException {
        Customer customer = getById(id);
        CustomerStatus[] statusList = CustomerStatus.values();
        CustomerStatus newStatus = CustomerStatus.AVAILABLE;

        for (int i = 0; i < statusList.length; i++) {
            if (statusList[i].toString().equals(customer.getStatus())) {
                newStatus = i + 1 < statusList.length ? statusList[i + 1] : CustomerStatus.AVAILABLE;
                break;
            }
        }

        if (newStatus.equals(CustomerStatus.AVAILABLE)) {
            orderRepository.deleteAllByCustomerId(customer.getId());
            partialPaymentRepository.deleteAllByCustomerId(customer.getId());
        }

        customer.setStatus(newStatus.toString());
        customerRepository.save(customer);
    }

}
