package com.ws101.longcop.dayday.EcommerceApi.Controller;

import com.ws101.longcop.dayday.EcommerceApi.Model.Product;
import com.ws101.longcop.dayday.EcommerceApi.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ws101.longcop.dayday.EcommerceApi.Exception.ResourceNotFoundException;

import java.util.List;

/**
 * REST Controller responsible for exposing product management HTTP endpoints.
 * Maps incoming HTTP operations onto the underlying business logic service layer.
 * * @author Longcop, Antonio Jr. N.
 * @see ProductService
 */
@RestController
@RequestMapping("/api/v1/products") // Sets base route mapping as specified by specs
public class ProductController {

    private final ProductService productService;

    /**
     * Dependency injection constructor to link our service logic layer.
     * * @param productService the initialized core product backend service
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Endpoint to fetch all standard items currently saved in memory.
     * * @return a List of all registered products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // Returns 200 OK
    }

    /**
     * Endpoint to fetch a singular targeted product structure matching an explicit ID.
     * * @param id unique string tracking parameter of the item
     * @return the matched target inside a ResponseEntity container
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " was not found."));
        return ResponseEntity.ok(product);
    }

    /**
     * Endpoint designed to accept custom search parameter strings to isolate values.
     * * @param filterType target field to analyze (e.g., name, category)
     * @param filterValue search term query sequence string
     * @return a list matching structural parameters
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {
        List<Product> filtered = productService.filterProducts(filterType, filterValue);
        return ResponseEntity.ok(filtered);
    }

    /**
     * Endpoint responsible for parsing and writing a brand new product item entry.
     * * @param product incoming JSON content model data structure body
     * @return the generated product with its appended auto-increment sequence ID
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(201).body(savedProduct); // Returns 201 Created explicitly
    }

    /**
     * Endpoint designed to wipe out and overwrite an item entirely matching an explicit ID.
     * * @param id target product lookup key string to overwrite
     * @param product replacement payload parameters mapping to structural attributes
     * @return the modified domain state result
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint processing explicit collection array removal protocols matching a precise key.
     * * @param id target item lookup key to eliminate
     * @return a completely empty content structural code status handler
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean isRemoved = productService.deleteProduct(id);
        if (isRemoved) {
            return ResponseEntity.noContent().build(); // Returns 204 No Content upon success
        }
        return ResponseEntity.notFound().build(); // 404 if item didn't exist to delete
    }
}