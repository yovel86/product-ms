package com.projects.productms.services;

import com.projects.productms.exceptions.ProductNotFoundException;
import com.projects.productms.models.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

}
