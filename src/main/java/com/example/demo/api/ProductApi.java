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

import com.example.demo.api.dto.NewProductDTO;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductCategory;
import com.example.demo.domain.exception.InvalidProductCategoryException;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        try {
            Product product = productService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid NewProductDTO newProductDTO) throws InvalidProductCategoryException {
        Product createdProduct = productService.createProduct(newProductDTO);
        URI resourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> upadteProduct(@PathVariable UUID id, @RequestBody @Valid NewProductDTO updateProduct) throws InvalidProductCategoryException {
        try {
            productService.updateProuct(id, updateProduct);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/categories")
    public ResponseEntity<String[]> getAllCategories() {
        String[] categories = new String[ProductCategory.values().length];

        for (int i = 0; i < ProductCategory.values().length; i++) {
            categories[i] = ProductCategory.values()[i].toString();
        }

        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

}
