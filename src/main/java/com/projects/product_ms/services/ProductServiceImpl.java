package com.projects.product_ms.services;

import com.projects.product_ms.exceptions.ProductNotFoundException;
import com.projects.product_ms.models.Category;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository  productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        return getProduct(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description, double price, String image, String categoryName, int availableQuantity) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        product.setAvailableQuantity(availableQuantity);
        Category category = this.categoryService.createCategory(categoryName);
        product.setCategory(category);
        return this.productRepository.save(product);
    }

    @Override
    public Product updatePrice(long productId, double updatedPrice) throws ProductNotFoundException {
        Product product = getProduct(productId);
        product.setPrice(updatedPrice);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateImage(long productId, String updatedImageUrl) throws ProductNotFoundException {
        Product product = getProduct(productId);
        product.setImage(updatedImageUrl);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateAvailableQuantity(long productId, int updatedQuantity) throws ProductNotFoundException {
        Product product = getProduct(productId);
        product.setAvailableQuantity(updatedQuantity);
        return this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(long productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        this.productRepository.delete(product);
    }

    private Product getProduct(long productId) throws ProductNotFoundException {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if(productOptional.isEmpty()) throw new ProductNotFoundException("Invalid Product ID");
        return productOptional.get();
    }

    @Override
    public List<Product> getProductsById(List<Long> productIds) {
        return this.productRepository.findByIdIn(productIds);
    }

}
