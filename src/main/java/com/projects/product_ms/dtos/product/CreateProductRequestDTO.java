package com.projects.product_ms.dtos.product;

import lombok.Data;

@Data
public class CreateProductRequestDTO {
    private String title;
    private String description;
    private double price;
    private String image;
    private String categoryName;
    private int availableQuantity;
}
