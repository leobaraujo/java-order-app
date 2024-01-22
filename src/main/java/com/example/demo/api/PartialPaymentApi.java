package com.example.demo.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
