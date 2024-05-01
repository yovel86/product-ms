package com.projects.product_ms.dtos.product;

import lombok.Data;

@Data
public class FakeStoreProductDTO {
    private long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
}
