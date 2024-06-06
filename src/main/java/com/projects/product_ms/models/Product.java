package com.projects.product_ms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data @Entity(name = "products")
public class Product extends BaseModel implements Serializable {
    private String title;
    private String description;
    private double price;
    private String image;
    private int availableQuantity;
    @ManyToOne
    private Category category;
}
