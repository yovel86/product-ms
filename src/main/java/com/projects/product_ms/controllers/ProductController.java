package com.projects.product_ms.controllers;

import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.dtos.product.*;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(@Qualifier("productService") ProductService productService) {
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
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @PostMapping
    public CreateProductResponseDTO createProduct(@RequestBody CreateProductRequestDTO requestDTO) {
        String title = requestDTO.getTitle();
        String description = requestDTO.getDescription();
        double price = requestDTO.getPrice();
        String image = requestDTO.getImage();
        String categoryName = requestDTO.getCategoryName();
        CreateProductResponseDTO responseDTO = new CreateProductResponseDTO();
        try {
            Product product = this.productService.createProduct(title, description, price, image, categoryName);
            responseDTO.setProduct(product);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @PatchMapping("/{id}/price")
    public UpdatePriceResponseDTO updatePrice(@PathVariable("id") long productId, @RequestBody UpdatePriceRequestDTO requestDTO) {
        double price = requestDTO.getPrice();
        UpdatePriceResponseDTO responseDTO = new UpdatePriceResponseDTO();
        try {
            Product product = this.productService.updatePrice(productId, price);
            responseDTO.setProduct(product);
            responseDTO.setMessage("Price of product with ID: " + productId + ", has been UPDATED");
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @PatchMapping("/{id}/image")
    public UpdateImageResponseDTO updateImage(@PathVariable("id") long productId, @RequestBody UpdateImageRequestDTO requestDTO) {
        String imageUrl = requestDTO.getImage();
        UpdateImageResponseDTO responseDTO = new UpdateImageResponseDTO();
        try {
            Product product = this.productService.updateImage(productId, imageUrl);
            responseDTO.setProduct(product);
            responseDTO.setMessage("Image of product with ID: " + productId + ", has been UPDATED");
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @DeleteMapping("/{id}")
    public DeleteProductResponseDTO deleteProduct(@PathVariable("id") long productId) {
        DeleteProductResponseDTO responseDTO = new DeleteProductResponseDTO();
        try {
            this.productService.deleteProduct(productId);
            responseDTO.setMessage("Product with ID: " + productId + " has been deleted");
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

}
