package com.projects.product_ms.models;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity(name = "products")
public class Product extends BaseModel {
    private String title;
    private String description;
    private double price;
    private String image;
    private int availableQuantity;
    @ManyToOne
    private Category category;
}
