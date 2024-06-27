package com.web.service;

import com.web.dto.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> fetchRecipes(int start, int end);
    int getTotalCount(); // 총 레시피 수를 가져오는 메서드
}
