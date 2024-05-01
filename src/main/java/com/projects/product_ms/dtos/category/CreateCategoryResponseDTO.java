package com.projects.product_ms.dtos.category;

import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.models.Category;
import lombok.Data;

@Data
public class CreateCategoryResponseDTO {
    private Category category;
    private ResponseStatus responseStatus;
}
