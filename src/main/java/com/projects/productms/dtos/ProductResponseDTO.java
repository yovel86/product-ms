package com.projects.productms.dtos;

import com.projects.productms.models.Product;
import lombok.Data;

@Data
public class ProductResponseDTO {
    Product product;
    ResponseStatus responseStatus;
    String errorMsg;
}
