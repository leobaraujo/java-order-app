package com.example.demo.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductApi {

    private ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> productList = productService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

}
