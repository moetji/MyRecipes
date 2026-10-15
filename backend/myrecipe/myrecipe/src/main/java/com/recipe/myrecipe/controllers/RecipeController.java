package com.recipe.myrecipe.controllers;

import com.recipe.myrecipe.entity.Recipe;
import com.recipe.myrecipe.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // GET ALL RECIPES
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {

        return ResponseEntity.ok(
                recipeService.getAllRecipes()
        );
    }

    // GET ONE RECIPE
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Integer id) {

        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADD RECIPE
    @PostMapping
    public ResponseEntity<?> createRecipe(
            @RequestBody Recipe recipe) {

        try {

            Recipe savedRecipe =
                    recipeService.createRecipe(recipe);

            return ResponseEntity.ok(savedRecipe);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // UPDATE RECIPE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(
            @PathVariable Integer id,
            @RequestBody Recipe recipe) {

        try {

            Recipe updatedRecipe =
                    recipeService.updateRecipe(id, recipe);

            return ResponseEntity.ok(updatedRecipe);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // DELETE RECIPE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(
            @PathVariable Integer id) {

        try {

            recipeService.deleteRecipe(id);

            return ResponseEntity.ok(
                    "Recipe deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // SEARCH RECIPES
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam String name) {

        return ResponseEntity.ok(
                recipeService.searchRecipes(name)
        );
    }

    // GET RECIPES BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Recipe>> getRecipesByCategory(
            @PathVariable Integer categoryId) {

        return ResponseEntity.ok(
                recipeService.getRecipesByCategory(categoryId)
        );
    }
}