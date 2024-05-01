package com.projects.product_ms.utils;

import com.projects.product_ms.dtos.product.FakeStoreProductDTO;
import com.projects.product_ms.models.Category;
import com.projects.product_ms.models.Product;

public class ProductUtils {

    public static Product convertDtoToProduct(FakeStoreProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        Category category = new Category();
        category.setName(productDTO.getCategory());
        product.setCategory(category);
        return product;
    }

}
