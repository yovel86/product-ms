package com.projects.productms.dtos;

import com.projects.productms.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductsResponseDTO {
    List<Product> products;
    ResponseStatus responseStatus;
}
