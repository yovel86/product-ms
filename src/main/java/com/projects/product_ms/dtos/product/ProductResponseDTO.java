package com.projects.product_ms.dtos.product;

import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.models.Product;
import lombok.Data;

@Data
public class ProductResponseDTO {
    private Product product;
    private String message;
    private ResponseStatus responseStatus;
}
