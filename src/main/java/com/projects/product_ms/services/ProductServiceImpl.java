package com.projects.product_ms.services;

import com.projects.product_ms.dtos.PagedResult;
import com.projects.product_ms.exceptions.ProductNotFoundException;
import com.projects.product_ms.models.Category;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository  productRepository;
    private final CategoryService categoryService;
    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8082/orders";

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, WebClient webClient) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.webClient = webClient;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        return getProduct(productId);
    }

    @Override
    public PagedResult<Product> getAllProducts(int pageSize, int pageNo, String[] sortParams) {
        pageNo = pageNo <= 1 ? 0 : pageNo - 1; // pages - 0 based index
        pageSize = Math.min(pageSize, 20);
        Sort sort = buildSortFromParams(sortParams);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> productPage = this.productRepository.findAll(pageable);
        return new PagedResult<>(
                productPage.getContent(),
                productPage.getTotalElements(),
                productPage.getNumber() + 1,
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.hasNext(),
                productPage.hasPrevious()
        );
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

    @Override
    public List<Product> getTrendingProducts() {
        List<Long> trendingProductIds = getTrendingProductIds();
        return this.productRepository.findByIdIn(trendingProductIds);
    }

    private List<Long> getTrendingProductIds() {
        return this.webClient
                .get()
                .uri(BASE_URL + "/trending")
                .retrieve()
                .bodyToFlux(Long.class)
                .collectList()
                .block();
    }

    private Sort buildSortFromParams(String[] params) {
        Sort sort = Sort.by(Sort.Order.asc("id"));
        if(params != null && params.length > 0) {
            Sort.Order[] orders = new Sort.Order[params.length];
            for(int i = 0; i < params.length; i++) {
                String[] split = params[i].split(":");
                String field = split[0];
                String direction = split[1];
                if(direction.equalsIgnoreCase("desc")) {
                    orders[i] = Sort.Order.desc(field);
                } else {
                    orders[i] = Sort.Order.asc(field);
                }
            }
            sort = Sort.by(orders);
        }
        return sort;
    }

}
