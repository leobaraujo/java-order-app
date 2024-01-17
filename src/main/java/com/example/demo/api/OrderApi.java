package com.example.demo.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.Order;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    private OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orderList = orderService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }
}
