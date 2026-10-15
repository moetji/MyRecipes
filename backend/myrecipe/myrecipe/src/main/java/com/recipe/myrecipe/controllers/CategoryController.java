package com.recipe.myrecipe.controllers;

import com.recipe.myrecipe.entity.Category;
import com.recipe.myrecipe.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET ALL CATEGORIES
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    // GET ONE CATEGORY
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable Integer id) {

        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE CATEGORY
    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody Category category) {

        try {

            Category savedCategory =
                    categoryService.createCategory(category);

            return ResponseEntity.ok(savedCategory);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // UPDATE CATEGORY
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Integer id,
            @RequestBody Category category) {

        try {

            Category updatedCategory =
                    categoryService.updateCategory(
                            id,
                            category
                    );

            return ResponseEntity.ok(updatedCategory);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // DELETE CATEGORY
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Integer id) {

        try {

            categoryService.deleteCategory(id);

            return ResponseEntity.ok(
                    "Category deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}