package com.projects.product_ms.services;

import com.projects.product_ms.dtos.PagedResult;
import com.projects.product_ms.dtos.product.FakeStoreProductDTO;
import com.projects.product_ms.exceptions.ProductNotFoundException;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService {

    private final WebClient webClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String BASE_URL = "https://fakestoreapi.com/products";

    @Autowired
    public FakeStoreProductServiceImpl(WebClient webClient, RedisTemplate<String, Object> redisTemplate) {
        this.webClient = webClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        Product cachedProduct = (Product) this.redisTemplate.opsForHash().get("PRODUCTS", "products_" + productId);
        if(cachedProduct != null) return cachedProduct;
        FakeStoreProductDTO productDTO = this.webClient
                                             .get()
                                             .uri(BASE_URL + "/" + productId)
                                             .retrieve()
                                             .bodyToMono(FakeStoreProductDTO.class).block();
        if(productDTO == null) throw new ProductNotFoundException("Invalid Product ID");
        Product product = ProductUtils.convertDtoToProduct(productDTO);
        this.redisTemplate.opsForHash().put("PRODUCTS", "products_" + productId, product);
        return product;
    }

    @Override
    public PagedResult<Product> getAllProducts(int pageSize, int pageNo, String[] sortBy) {
        FakeStoreProductDTO[] productDTOS = this.webClient
                                                .get()
                                                .uri(BASE_URL)
                                                .retrieve()
                                                .bodyToMono(FakeStoreProductDTO[].class).block();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDTO dto: productDTOS) {
            products.add(ProductUtils.convertDtoToProduct(dto));
        }
        return null;
    }

    @Override
    public Product createProduct(String title, String description, double price, String image, String categoryName, int availableQuantity) {
        return null;
    }

    @Override
    public Product updatePrice(long productId, double updatedPrice) throws ProductNotFoundException  {
        return null;
    }

    @Override
    public Product updateImage(long productId, String updatedImageUrl) throws ProductNotFoundException  {
        return null;
    }

    @Override
    public Product updateAvailableQuantity(long productId, int updatedQuantity) throws ProductNotFoundException {
        return null;
    }

    @Override
    public void deleteProduct(long productId) throws ProductNotFoundException { }

    @Override
    public List<Product> getProductsById(List<Long> productIds) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getTrendingProducts() {
        return null;
    }
}
