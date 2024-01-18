package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.NewOrderDTO;
import com.example.demo.api.dto.UpdateOrderDTO;
import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.exception.InvalidEntityIdException;
import com.example.demo.domain.repository.OrderRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImp implements OrderService {

    private OrderRepository orderRepository;
    private CustomerService customerService;
    private ProductService productService;

    public OrderServiceImp(OrderRepository orderRepository, CustomerService customerService,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(UUID id) throws NoSuchElementException {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Order createOrder(NewOrderDTO newOrder) throws InvalidEntityIdException {
        try {
            boolean isProductOnCustomer = existsProductOnCustomer(UUID.fromString(newOrder.productId()),
                    UUID.fromString(newOrder.customerId()));

            if (isProductOnCustomer) {
                throw new InvalidEntityIdException("Product already exists on Customer's order(s).");
            }

            Customer customer = customerService.getById(UUID.fromString(newOrder.customerId()));
            Product product = productService.getById(UUID.fromString(newOrder.productId()));
            Order order = new Order();

            order.setCustomer(customer);
            order.setProduct(product);
            order.setQuantity(newOrder.quantity());
            order.setIsActive(true);

            return orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityIdException("Invalid UUID format.");
        } catch (NoSuchElementException e) {
            throw new InvalidEntityIdException("Customer or Product not found.");
        }
    }

    @Override
    @Transactional
    public void updateOrder(UUID id, UpdateOrderDTO updateOrderDTO) throws NoSuchElementException {
        try {
            Order order = getById(id);
            boolean isProductOnCustomer = existsProductOnCustomer(UUID.fromString(updateOrderDTO.productId()),
                    UUID.fromString(updateOrderDTO.customerId()));

            if (!isProductOnCustomer) {
                throw new InvalidEntityIdException("Product does not exists on Customer's order(s).");
            }

            order.setQuantity(updateOrderDTO.quantity());
            order.setIsActive(updateOrderDTO.isActive());

            orderRepository.save(order);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Order not found: " + id);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Invalid UUID format.");
        } catch (InvalidEntityIdException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }

    private boolean existsProductOnCustomer(UUID productId, UUID customerId) {
        return orderRepository.findByProductIdAndCustomerId(productId, customerId).isPresent();
    }

}
