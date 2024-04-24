package com.projects.productms.controllers;

import com.projects.productms.dtos.ProductResponseDTO;
import com.projects.productms.dtos.ProductsResponseDTO;
import com.projects.productms.dtos.ResponseStatus;
import com.projects.productms.models.Product;
import com.projects.productms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductsResponseDTO getProducts() {
        ProductsResponseDTO responseDTO = new ProductsResponseDTO();
        responseDTO.setProducts(this.productService.getAllProducts());
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDTO;
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") long id) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        try {
            Product product = this.productService.getProductById(id);
            responseDTO.setProduct(product);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
            responseDTO.setErrorMsg(e.getMessage());
        }
        return responseDTO;
    }

}
