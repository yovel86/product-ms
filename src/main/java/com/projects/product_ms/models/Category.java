package com.projects.product_ms.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Data @Entity(name = "categories")
public class Category extends BaseModel implements Serializable {
    private String name;
}
