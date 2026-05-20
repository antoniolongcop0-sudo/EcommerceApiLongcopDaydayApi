package com.ws101.longcop.dayday.EcommerceApi.Controller;

import com.ws101.longcop.dayday.EcommerceApi.Model.Product;
import com.ws101.longcop.dayday.EcommerceApi.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller responsible for exposing product management HTTP endpoints.
 * Intercepts client incoming network requests and routes them into the database service tier.
 * @author Longcop, Antonio Jr. N.
 */
@CrossOrigin(origins = "http://localhost:5500", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint route path targeting: GET /api/v1/products
     * Pulls the comprehensive active entity collection from the database rows.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Endpoint route path targeting: GET /api/v1/products/{id}
     * Extracts a single distinct inventory element by its numeric identifier pointer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Endpoint route path targeting: GET /api/v1/products/filter?category=value
     * Filters inventory matching a specific categorical grouping tag parameter.
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    /**
     * Endpoint route path targeting: POST /api/v1/products
     * Validates data structures and adds a new record entry row into the database.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /**
     * Endpoint route path targeting: PUT /api/v1/products/{id}
     * Updates and rewrites parameter properties belonging to a specific database product record row.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    /**
     * Endpoint route path targeting: DELETE /api/v1/products/{id}
     * Deletes a target database product entity record row completely.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}