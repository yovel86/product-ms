package com.projects.product_ms.controllers;

import com.projects.product_ms.components.AuthUtils;
import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.dtos.product.*;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final AuthUtils authUtils;

    @Autowired
    public ProductController(@Qualifier("productService") ProductService productService, AuthUtils authUtils) {
        this.productService = productService;
        this.authUtils = authUtils;
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
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequestDTO requestDTO, @RequestHeader("Auth") String token) {
        try {
            if(!authUtils.validateToken(token)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Product product = this.productService.createProduct(
                requestDTO.getTitle(),
                requestDTO.getDescription(),
                requestDTO.getPrice(),
                requestDTO.getImage(),
                requestDTO.getCategoryName(),
                requestDTO.getAvailableQuantity()
        );
        return new ResponseEntity<>(product, HttpStatus.CREATED);
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

    @PatchMapping("/{id}/available_quantity")
    public UpdateQuantityResponseDTO updateAvailableQuantity(@PathVariable("id") long productId, @RequestBody UpdateQuantityRequestDTO requestDTO) {
        int quantity = requestDTO.getQuantity();
        UpdateQuantityResponseDTO responseDTO = new UpdateQuantityResponseDTO();
        try {
            Product product = this.productService.updateAvailableQuantity(productId, quantity);
            responseDTO.setProduct(product);
            responseDTO.setMessage("Quantity of product with ID: " + productId + ", has been UPDATED");
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

    @PostMapping("/details")
    public List<Product> getProductsById(@RequestBody GetProductsByIdRequestDTO requestDTO) {
        List<Long> productIds = requestDTO.getProductIds();
        System.out.println(productIds);
        List<Product> products = new ArrayList<>();
        try {
            products = this.productService.getProductsById(productIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @GetMapping("/trending")
    public List<Product> getTrendingProducts() {
        return this.productService.getTrendingProducts();
    }


}
