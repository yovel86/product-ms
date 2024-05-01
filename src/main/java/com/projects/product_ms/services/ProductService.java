package com.projects.product_ms.services;

import com.projects.product_ms.exceptions.ProductNotFoundException;
import com.projects.product_ms.models.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(String title, String description, double price, String image, String categoryName);

    Product updatePrice(long productId, double updatedPrice) throws ProductNotFoundException;

    Product updateImage(long productId, String updatedImageUrl) throws ProductNotFoundException;

    void deleteProduct(long productId) throws ProductNotFoundException;

}
