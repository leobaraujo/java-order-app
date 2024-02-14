package com.example.demo.api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.Customer;
import com.example.demo.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer")
@SecurityRequirement(name = "Bearer Token")
public class CustomerApi {

    private CustomerService customerService;

    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all customers as array")
    })
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customerList = customerService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(customerList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the customer"),
            @ApiResponse(responseCode = "404", description = "If customer is not found")
    })
    public ResponseEntity<Customer> getById(@PathVariable UUID id) {
        try {
            Customer customer = customerService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Toggle through possible status. When new status is AVAILABLE all orders and partial payment are removed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "If customer is not found")
    })
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id) {
        try {
            customerService.updateStatus(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
