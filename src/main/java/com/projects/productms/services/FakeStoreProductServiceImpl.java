package com.projects.productms.services;

import com.projects.productms.dtos.FakeStoreProductDTO;
import com.projects.productms.exceptions.ProductNotFoundException;
import com.projects.productms.models.Product;
import com.projects.productms.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductServiceImpl implements ProductService {

    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long productId) throws ProductNotFoundException {
        FakeStoreProductDTO productDTO = this.restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDTO.class);
        if(productDTO == null) throw new ProductNotFoundException("Invalid Product ID");
        return ProductUtils.convertDtoToProduct(productDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO[] productDTOS = this.restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDTO dto: productDTOS) {
            products.add(ProductUtils.convertDtoToProduct(dto));
        }
        return products;
    }

}
