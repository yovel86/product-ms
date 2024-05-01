package com.projects.product_ms.services;

import com.projects.product_ms.exceptions.CategoryNotFoundException;
import com.projects.product_ms.models.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name);

    List<Category> getCategories();

    Category getCategoryById(long categoryId) throws CategoryNotFoundException;

    Category updateName(long categoryId, String updatedName) throws CategoryNotFoundException;

    void deleteCategory(long categoryId) throws CategoryNotFoundException;

}
