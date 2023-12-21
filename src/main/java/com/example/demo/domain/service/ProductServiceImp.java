package com.example.demo.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Product;
import com.example.demo.domain.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImp implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(UUID id) throws Exception {
        return productRepository.findById(id).orElseThrow();
    }

}
