package com.web.service;

import com.web.dto.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> fetchRecipes(int start, int end);
    int getTotalCount();
    Recipe getRecipeByName(String name);
}
