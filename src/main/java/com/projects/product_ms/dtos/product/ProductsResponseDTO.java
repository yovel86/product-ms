package com.projects.product_ms.dtos.product;

import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductsResponseDTO {
    List<Product> products;
    ResponseStatus responseStatus;
}
