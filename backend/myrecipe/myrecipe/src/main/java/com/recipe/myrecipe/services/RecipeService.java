package com.recipe.myrecipe.services;

import com.recipe.myrecipe.entity.Recipe;
import com.recipe.myrecipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // Get all recipes
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Get one recipe
    public Optional<Recipe> getRecipeById(Integer id) {
        return recipeRepository.findById(id);
    }

    // Add a recipe
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // Update a recipe
    public Recipe updateRecipe(Integer id, Recipe recipeDetails) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Recipe not found"));

        recipe.setName(recipeDetails.getName());
        recipe.setDescription(recipeDetails.getDescription());
        recipe.setInstructions(recipeDetails.getInstructions());
        recipe.setPrepTime(recipeDetails.getPrepTime());
        recipe.setCookTime(recipeDetails.getCookTime());
        recipe.setServings(recipeDetails.getServings());
        recipe.setImage(recipeDetails.getImage());
        recipe.setCategory(recipeDetails.getCategory());

        return recipeRepository.save(recipe);
    }

    // Delete a recipe
    public void deleteRecipe(Integer id) {

        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }

        recipeRepository.deleteById(id);
    }

    // Search recipes
    public List<Recipe> searchRecipes(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name);
    }

    // Get recipes by category
    public List<Recipe> getRecipesByCategory(Integer categoryId) {
        return recipeRepository.findByCategoryCId(categoryId);
    }
}