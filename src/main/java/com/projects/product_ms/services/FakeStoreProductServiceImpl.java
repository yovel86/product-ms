package com.projects.product_ms.services;

import com.projects.product_ms.dtos.product.FakeStoreProductDTO;
import com.projects.product_ms.exceptions.ProductNotFoundException;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService {

    private final WebClient webClient;
    private final String BASE_URL = "https://fakestoreapi.com/products";

    @Autowired
    public FakeStoreProductServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        FakeStoreProductDTO productDTO = this.webClient
                                             .get()
                                             .uri(BASE_URL + "/" + productId)
                                             .retrieve()
                                             .bodyToMono(FakeStoreProductDTO.class).block();
        if(productDTO == null) throw new ProductNotFoundException("Invalid Product ID");
        return ProductUtils.convertDtoToProduct(productDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO[] productDTOS = this.webClient
                                                .get()
                                                .uri(BASE_URL)
                                                .retrieve()
                                                .bodyToMono(FakeStoreProductDTO[].class).block();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDTO dto: productDTOS) {
            products.add(ProductUtils.convertDtoToProduct(dto));
        }
        return products;
    }

    @Override
    public Product createProduct(String title, String description, double price, String image, String categoryName) {
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
    public void deleteProduct(long productId) throws ProductNotFoundException { }

}
