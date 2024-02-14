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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product")
@SecurityRequirement(name = "Bearer Token")
public class ProductApi {

    private ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all product as array")
    })
    public ResponseEntity<List<Product>> getAll() {
        List<Product> productList = productService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the product"),
            @ApiResponse(responseCode = "404", description = "If product is not found")
    })
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        try {
            Product product = productService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If product category is invalid")
    })
    public ResponseEntity<Void> createProduct(@RequestBody @Valid NewProductDTO newProductDTO) throws InvalidProductCategoryException {
        Product createdProduct = productService.createProduct(newProductDTO);
        URI resourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If product category is invalid")
    })
    public ResponseEntity<Void> updateProduct(@PathVariable UUID id, @RequestBody @Valid NewProductDTO updateProduct) throws InvalidProductCategoryException {
        try {
            productService.updateProuct(id, updateProduct);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/categories")
    @Operation(summary = "Get all product categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all product category as array")
    })
    public ResponseEntity<String[]> getAllCategories() {
        String[] categories = new String[ProductCategory.values().length];

        for (int i = 0; i < ProductCategory.values().length; i++) {
            categories[i] = ProductCategory.values()[i].toString();
        }

        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

}
