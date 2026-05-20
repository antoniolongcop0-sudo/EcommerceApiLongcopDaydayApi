package com.ws101.longcop.dayday.EcommerceApi.Service;

import com.ws101.longcop.dayday.EcommerceApi.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Service class for product-related operations.
 * Provides business logic for filtering, searching, and managing products in memory.
 * * @author Longcop, Antonio Jr. N.
 * @see Product
 */
@Service
public class ProductService {

    // In-memory collection storage approach to hold product data without a database
    private final List<Product> productList = new ArrayList<>();

    // Thread-safe counter for generating unique sequential product IDs
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public ProductService() {
        // Initialize with 10 sample products as required by lab specs
        generateSampleData();
    }

    /**
     * Helper method to pre-populate our in-memory list with initial sample items.
     */
    private void generateSampleData() {
        String[] categories = {"Electronics", "Clothing", "Books", "Home Appliances"};
        for (int i = 1; i <= 10; i++) {
            String id = String.valueOf(idCounter.getAndIncrement()); // Unique sequential ID strategy
            String category = categories[i % categories.length];
            productList.add(new Product(
                    id,
                    "Sample Product " + i,
                    "This is a premium high-quality description for product number " + i,
                    19.99 * i,
                    category,
                    10 * i,
                    "https://images.example.com/product" + i + ".jpg"
            ));
        }
    }

    /**
     * Retrieves all products currently stored in the memory list.
     *
     * @return a {@code List<Product>} containing all available items.
     */
    public List<Product> getAllProducts() {
        return productList;
    }

    /**
     * Finds a single product by its unique ID.
     *
     * @param id the unique string identifier of the product.
     * @return an {@code Optional<Product>} containing the product if found, or empty if not.
     */
    public Optional<Product> getProductById(String id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Creates and stores a new product with an automatically assigned unique ID.
     *
     * @param product the product object details containing data from the client.
     * @return the saved {@code Product} object complete with its new unique ID.
     */
    public Product createProduct(Product product) {
        String uniqueId = String.valueOf(idCounter.getAndIncrement()); // Keep tracking unique auto-increment IDs
        product.setId(uniqueId);
        productList.add(product);
        return product;
    }

    /**
     * Fully replaces an existing product's details matching the given ID.
     *
     * @param id the unique ID of the product to overwrite.
     * @param updatedProduct the full collection of new values to apply.
     * @return an {@code Optional<Product>} containing the altered product, or empty if it doesn't exist.
     */
    public Optional<Product> updateProduct(String id, Product updatedProduct) {
        return getProductById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
            return existingProduct;
        });
    }

    /**
     * Deletes a specific product from the in-memory array list.
     *
     * @param id the unique identifier of the target product to be erased.
     * @return true if an item matched and was safely deleted; false otherwise.
     */
    public boolean deleteProduct(String id) {
        return productList.removeIf(product -> product.getId().equals(id));
    }

    /**
     * Filters products based on specific criteria types and dynamic matching strings.
     *
     * @param filterType the target field to examine (e.g., "name", "category").
     * @param filterValue the target search phrase to narrow down entries.
     * @return a filtered {@code List<Product>} containing matches.
     */
    public List<Product> filterProducts(String filterType, String filterValue) {
        if (filterType == null || filterValue == null) {
            return productList;
        }

        String lowerValue = filterValue.toLowerCase();

        switch (filterType.toLowerCase()) {
            case "name":
                return productList.stream()
                        .filter(p -> p.getName().toLowerCase().contains(lowerValue))
                        .collect(Collectors.toList());
            case "category":
                return productList.stream()
                        .filter(p -> p.getCategory().toLowerCase().equalsIgnoreCase(lowerValue))
                        .collect(Collectors.toList());
            default:
                return productList;
        }
    }
}