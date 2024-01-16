package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.NewProductDTO;
import com.example.demo.domain.entity.Product;

@Service
public interface ProductService {

    public List<Product> getAll();

    public Product getById(UUID id) throws NoSuchElementException;

    public Product createProduct(NewProductDTO newProduct);

    public void updateProuct(UUID id, NewProductDTO updatedProduct) throws NoSuchElementException;

}
