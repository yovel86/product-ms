package com.projects.product_ms.dtos.product;

import com.projects.product_ms.dtos.ResponseStatus;
import lombok.Data;

@Data
public class DeleteProductResponseDTO {
    private String message;
    private ResponseStatus responseStatus;
}
