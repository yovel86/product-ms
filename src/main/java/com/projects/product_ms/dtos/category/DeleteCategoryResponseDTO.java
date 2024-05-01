package com.projects.product_ms.dtos.category;

import com.projects.product_ms.dtos.ResponseStatus;
import lombok.Data;

@Data
public class DeleteCategoryResponseDTO {
    private String message;
    private ResponseStatus responseStatus;
}
