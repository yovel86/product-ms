package com.projects.product_ms.dtos;

import com.projects.product_ms.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class ListResponse {
    private List<Product> products;
    private String message;
    private ResponseStatus responseStatus;
}
