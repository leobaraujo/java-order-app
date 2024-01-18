package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.demo.api.dto.NewProductDTO;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.exception.InvalidProductCategoryException;

public interface ProductService {

    public List<Product> getAll();

    public Product getById(UUID id) throws NoSuchElementException;

    public Product createProduct(NewProductDTO newProduct) throws InvalidProductCategoryException;

    public void updateProuct(UUID id, NewProductDTO updatedProduct) throws InvalidProductCategoryException;

    public void deleteProduct(UUID id);

}
