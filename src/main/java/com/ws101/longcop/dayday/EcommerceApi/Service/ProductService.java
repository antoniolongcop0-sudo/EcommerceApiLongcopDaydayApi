package com.ws101.longcop.dayday.EcommerceApi.Service;

import com.ws101.longcop.dayday.EcommerceApi.Model.Product;
import com.ws101.longcop.dayday.EcommerceApi.Repository.ProductRepository;
import com.ws101.longcop.dayday.EcommerceApi.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class orchestrating transactional business logic routines
 * by retrieving, persisting, and mutating data directly inside the MySQL database layer.
 * @author Longcop, Antonio Jr. N.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Extracts all product record rows stored in the MySQL products table.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Locates a single specific product record by its primary key ID identifier.
     * Throws a structured exception if the key reference does not exist.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " was not found."));
    }

    /**
     * Queries and filters the product inventory rows matching a category string.
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    /**
     * Validates data integrity and saves a brand new product record entity row.
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Resolves an existing record matching an ID pointer and overwrites its fields.
     */
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());
        existingProduct.setImageUrl(productDetails.getImageUrl());

        return productRepository.save(existingProduct);
    }

    /**
     * Deletes a target database product entity record row completely out of disk memory.
     */
    public void deleteProduct(Long id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }
}