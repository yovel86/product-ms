package com.projects.product_ms.services;

import com.projects.product_ms.exceptions.CategoryNotFoundException;
import com.projects.product_ms.models.Category;
import com.projects.product_ms.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name) {
        Optional<Category> categoryOptional = this.categoryRepository.findByNameIgnoreCase(name);
        if(categoryOptional.isEmpty()) {
            Category category = new Category();
            category.setName(name.toUpperCase());
            return this.categoryRepository.save(category);
        }
        return categoryOptional.get();
    }

    @Override
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long categoryId) throws CategoryNotFoundException {
        return getCategory(categoryId);
    }

    @Override
    public Category updateName(long categoryId, String updatedName) throws CategoryNotFoundException {
        Category category = getCategory(categoryId);
        category.setName(updatedName);
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long categoryId) throws CategoryNotFoundException {
        Category category = getCategory(categoryId);
        this.categoryRepository.delete(category);
    }

    private Category getCategory(long categoryId) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()) throw new CategoryNotFoundException("Invalid Category ID");
        return categoryOptional.get();
    }

}
