package com.example.demo.api;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.api.dto.NewOrderDTO;
import com.example.demo.api.dto.UpdateOrderDTO;
import com.example.demo.domain.entity.Order;
import com.example.demo.domain.exception.InvalidEntityIdException;
import com.example.demo.service.OrderService;

import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable UUID id) {
        try {
            Order order = orderService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Order>> getAllByCustomerId(@PathVariable UUID id) {
        List<Order> customerOrders = orderService.getAllByCustomerId(id);

        return ResponseEntity.status(HttpStatus.OK).body(customerOrders);
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid NewOrderDTO newOrderDTO)
            throws InvalidEntityIdException {
        Order order = orderService.createOrder(newOrderDTO);
        URI resourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getId()).toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable UUID id,
            @RequestBody @Valid UpdateOrderDTO updateOrderDTO) throws NoSuchElementException {
        orderService.updateOrder(id, updateOrderDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable UUID id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
