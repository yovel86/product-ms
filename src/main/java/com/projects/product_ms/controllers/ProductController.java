package com.projects.product_ms.controllers;

import com.projects.product_ms.components.AuthUtils;
import com.projects.product_ms.dtos.ListResponse;
import com.projects.product_ms.dtos.Response;
import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.dtos.product.*;
import com.projects.product_ms.models.Product;
import com.projects.product_ms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> getProducts(@RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        response.setBody(this.productService.getAllProducts());
        response.setMessage("Retrieved All Products");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable("id") long id, @RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            Product product = this.productService.getProductById(id);
            response.setBody(product);
            response.setMessage("Retrieved Product with ID: " + id);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody CreateProductRequestDTO requestDTO, @RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        Product product = this.productService.createProduct(
                requestDTO.getTitle(),
                requestDTO.getDescription(),
                requestDTO.getPrice(),
                requestDTO.getImage(),
                requestDTO.getCategoryName(),
                requestDTO.getAvailableQuantity()
        );
        response.setBody(product);
        response.setMessage("Product Created");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<Response> updatePrice(@PathVariable("id") long productId, @RequestBody UpdatePriceRequestDTO requestDTO, @RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        double price = requestDTO.getPrice();
        try {
            Product product = this.productService.updatePrice(productId, price);
            response.setBody(product);
            response.setMessage("Price of product with ID: " + productId + ", has been UPDATED");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Response> updateImage(@PathVariable("id") long productId, @RequestBody UpdateImageRequestDTO requestDTO, @RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        String imageUrl = requestDTO.getImage();
        try {
            Product product = this.productService.updateImage(productId, imageUrl);
            response.setBody(product);
            response.setMessage("Image of product with ID: " + productId + ", has been UPDATED");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/available_quantity")
    public ResponseEntity<Response> updateAvailableQuantity(@PathVariable("id") long productId, @RequestBody UpdateQuantityRequestDTO requestDTO, @RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        int quantity = requestDTO.getQuantity();
        try {
            Product product = this.productService.updateAvailableQuantity(productId, quantity);
            response.setBody(product);
            response.setMessage("Quantity of product with ID: " + productId + ", has been UPDATED");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("id") long productId, @RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            this.productService.deleteProduct(productId);
            response.setMessage("Product with ID: " + productId + " has been deleted");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/details")
    public ResponseEntity<ListResponse> getProductsById(@RequestBody GetProductsByIdRequestDTO requestDTO, @RequestHeader("Auth") String token) {
        ListResponse response = new ListResponse();
        response.setResponseStatus(ResponseStatus.SUCCESS);
        try {
            if(!authUtils.validateToken(token)) {
                response.setMessage("Invalid Token");
                response.setResponseStatus(ResponseStatus.FAILURE);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        List<Long> productIds = requestDTO.getProductIds();
        try {
            List<Product> products = this.productService.getProductsById(productIds);
            response.setProducts(products);
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trending")
    public ResponseEntity<Response> getTrendingProducts(@RequestHeader("Auth") String token) {
        Response response = authenticateUser(token);
        if(response.getResponseStatus().equals(ResponseStatus.FAILURE)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        List<Product> trendingProducts = this.productService.getTrendingProducts();
        response.setBody(trendingProducts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Response authenticateUser(String token) {
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.SUCCESS);
        try {
            if(!authUtils.validateToken(token)) {
                response.setMessage("Invalid Token");
                response.setResponseStatus(ResponseStatus.FAILURE);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

}
