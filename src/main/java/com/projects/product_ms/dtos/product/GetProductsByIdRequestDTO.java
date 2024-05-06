package com.projects.product_ms.dtos.product;

import lombok.Data;

import java.util.List;

@Data
public class GetProductsByIdRequestDTO {
    private List<Long> productIds;
}
