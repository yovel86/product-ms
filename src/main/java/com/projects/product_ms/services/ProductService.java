package com.projects.product_ms.services;

import com.projects.product_ms.exceptions.ProductNotFoundException;
import com.projects.product_ms.models.Product;

import javax.swing.*;
import java.util.List;

public interface ProductService {

    Product getProductById(long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(String title, String description, double price, String image, String categoryName, int availableQuantity);

    Product updatePrice(long productId, double updatedPrice) throws ProductNotFoundException;

    Product updateImage(long productId, String updatedImageUrl) throws ProductNotFoundException;

    Product updateAvailableQuantity(long productId, int updatedQuantity) throws ProductNotFoundException;

    void deleteProduct(long productId) throws ProductNotFoundException;

    List<Product> getProductsById(List<Long> productIds) throws ProductNotFoundException;

    List<Product> getTrendingProducts();

}
