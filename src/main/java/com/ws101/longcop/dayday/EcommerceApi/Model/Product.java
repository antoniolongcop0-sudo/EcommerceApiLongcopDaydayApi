package com.ws101.longcop.dayday.EcommerceApi.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // Generates the default no-argument constructor required by Spring
@AllArgsConstructor // Generates a constructor with all fields for easy object creation
public class Product {

    private String id;          // Unique identifier for the product
    private String name;        // Product name
    private String description; // Detailed description of the product
    private double price;       // Price of the product
    private String category;    // Product category (e.g., Electronics, Clothing)
    private int stockQuantity;  // Current stock availability
    private String imageUrl;    // Optional product image link
}