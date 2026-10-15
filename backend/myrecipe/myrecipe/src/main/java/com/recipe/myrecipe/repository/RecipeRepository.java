package com.recipe.myrecipe.repository;

import com.recipe.myrecipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findByCategoryCId(Integer categoryId);

    List<Recipe> findByNameContainingIgnoreCase(String name);
}