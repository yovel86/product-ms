package com.projects.productms.utils;

import com.projects.productms.dtos.FakeStoreProductDTO;
import com.projects.productms.models.Category;
import com.projects.productms.models.Product;

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
