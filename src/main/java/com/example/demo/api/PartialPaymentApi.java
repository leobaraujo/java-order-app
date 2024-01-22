package com.example.demo.api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.PartialPayment;
import com.example.demo.service.PartialPaymentService;

@RestController
@RequestMapping("/api/partialpayment")
public class PartialPaymentApi {

    private PartialPaymentService partialPaymentService;

    public PartialPaymentApi(PartialPaymentService partialPaymentService) {
        this.partialPaymentService = partialPaymentService;
    }

    @GetMapping
    public ResponseEntity<List<PartialPayment>> getAll() {
        List<PartialPayment> partialPaymentsList = partialPaymentService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(partialPaymentsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartialPayment> getById(@PathVariable UUID id) {
        try {
            PartialPayment partialPayment = partialPaymentService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(partialPayment);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<PartialPayment>> getAllByCustomerId(@PathVariable UUID id) {
        List<PartialPayment> partialPaymentsList = partialPaymentService.getAllByCustomerId(id);

        return ResponseEntity.status(HttpStatus.OK).body(partialPaymentsList);
    }

}
