package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Product;

@Service
public interface ProductService {

    public List<Product> getAll();

    public Product getById(UUID id) throws Exception;

}
