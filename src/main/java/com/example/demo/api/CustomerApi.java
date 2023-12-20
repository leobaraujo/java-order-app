package com.example.demo.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.Customer;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerApi {

    private CustomerService customerService;

    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customerList = customerService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(customerList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable UUID id) {
        try {
            Customer customer = customerService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
