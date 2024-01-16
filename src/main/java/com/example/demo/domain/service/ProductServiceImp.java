package com.example.demo.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.NewProductDTO;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductCategory;
import com.example.demo.domain.exception.InvalidProductCategoryException;
import com.example.demo.domain.repository.ProductRepository;
import com.example.demo.service.ProductService;

import jakarta.transaction.Transactional;

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
    public Product getById(UUID id) throws NoSuchElementException {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Product createProduct(NewProductDTO newProduct) throws InvalidProductCategoryException {
        if (!isValidCategory(newProduct.category())) {
            throw new InvalidProductCategoryException("Invalid Product category: " + newProduct.category());
        }

        Product product = new Product();

        product.setName(newProduct.name());
        product.setBuyPrice(newProduct.buyPrice());
        product.setSellPrice(newProduct.sellPrice());
        product.setImgUrl(newProduct.imgUrl());
        product.setCategory(newProduct.category());

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProuct(UUID id, NewProductDTO updatedProduct) throws InvalidProductCategoryException {
        Product currentProduct = getById(id);

        if (!isValidCategory(updatedProduct.category())) {
            throw new InvalidProductCategoryException("Invalid Product category: " + updatedProduct.category());
        }

        currentProduct.setName(updatedProduct.name());
        currentProduct.setBuyPrice(updatedProduct.buyPrice());
        currentProduct.setSellPrice(updatedProduct.sellPrice());
        currentProduct.setImgUrl(updatedProduct.imgUrl());
        currentProduct.setCategory(updatedProduct.category());

        productRepository.save(currentProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    private boolean isValidCategory(String category) {
        for (int i = 0; i < ProductCategory.values().length; i++) {
            if (category.equals(ProductCategory.values()[i].toString())) {
                return true;
            }
        }

        return false;
    }

}
