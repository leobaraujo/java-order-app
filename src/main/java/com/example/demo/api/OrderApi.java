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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order")
@SecurityRequirement(name = "Bearer Token")
public class OrderApi {

    private OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all orders as array")
    })
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orderList = orderService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the order"),
            @ApiResponse(responseCode = "404", description = "If order is not found")
    })
    public ResponseEntity<Order> getById(@PathVariable UUID id) {
        try {
            Order order = orderService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get all orders from customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all orders as array")
    })
    public ResponseEntity<List<Order>> getAllByCustomerId(@PathVariable UUID id) {
        List<Order> customerOrders = orderService.getAllByCustomerId(id);

        return ResponseEntity.status(HttpStatus.OK).body(customerOrders);
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If is invalid id, customer or product not found, customer is not BUSY or order already exists on customer")
    })
    public ResponseEntity<Void> createOrder(@RequestBody @Valid NewOrderDTO newOrderDTO)
            throws InvalidEntityIdException {
        Order order = orderService.createOrder(newOrderDTO);
        URI resourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getId()).toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If is invalid id, order not found or product does not exists on customer's order(s)")
    })
    public ResponseEntity<Void> updateOrder(@PathVariable UUID id,
            @RequestBody @Valid UpdateOrderDTO updateOrderDTO) throws NoSuchElementException {
        orderService.updateOrder(id, updateOrderDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success")
    })
    public ResponseEntity<Void> deleteOrderById(@PathVariable UUID id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
