package com.projects.product_ms.controllers;

import com.projects.product_ms.dtos.ResponseStatus;
import com.projects.product_ms.dtos.category.*;
import com.projects.product_ms.models.Category;
import com.projects.product_ms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {
        return this.categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable("id") long categoryId) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        try {
            Category category = this.categoryService.getCategoryById(categoryId);
            responseDTO.setCategory(category);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @PostMapping
    public CreateCategoryResponseDTO createCategory(@RequestBody CreateCategoryRequestDTO requestDTO) {
        String categoryName = requestDTO.getName();
        CreateCategoryResponseDTO responseDTO = new CreateCategoryResponseDTO();
        try {
            Category category = this.categoryService.createCategory(categoryName);
            responseDTO.setCategory(category);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @PatchMapping("/{id}/name")
    public UpdateNameResponseDTO updateName(@PathVariable("id") long categoryId, @RequestBody UpdateNameRequestDTO requestDTO) {
        String productName = requestDTO.getName();
        UpdateNameResponseDTO responseDTO = new UpdateNameResponseDTO();
        try {
            Category category = this.categoryService.updateName(categoryId, productName);
            responseDTO.setCategory(category);
            responseDTO.setMessage("Category with ID: " + categoryId + ", has been UPDATED");
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @DeleteMapping("/{id}")
    public DeleteCategoryResponseDTO deleteCategory(@PathVariable("id") long categoryId) {
        DeleteCategoryResponseDTO responseDTO = new DeleteCategoryResponseDTO();
        try {
            this.categoryService.deleteCategory(categoryId);
            responseDTO.setMessage("Category with ID: " + categoryId + ", has been DELETED");
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

}
