package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Product;

@Service
public interface ProductService {

    public List<Product> getAll();

}
