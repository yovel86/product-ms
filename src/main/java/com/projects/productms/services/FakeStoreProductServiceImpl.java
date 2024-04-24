package com.projects.productms.services;

import com.projects.productms.dtos.FakeStoreProductDTO;
import com.projects.productms.exceptions.ProductNotFoundException;
import com.projects.productms.models.Product;
import com.projects.productms.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
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

}
