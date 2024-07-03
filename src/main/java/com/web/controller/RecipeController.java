package com.web.controller;

import com.web.dto.Recipe;
import com.web.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequestMapping("recipes")
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String getRecipes(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size) {
        List<Recipe> recipes = recipeService.fetchRecipes((page - 1) * size + 1, page * size);
        int totalRecipes = recipeService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalRecipes / size);

        // URL 인코딩된 레시피 이름 추가
        for (Recipe recipe : recipes) {
            String encodedName = UriUtils.encode(recipe.getRCP_NM(), StandardCharsets.UTF_8);
            recipe.setEncodedName(encodedName);
        }

        model.addAttribute("recipes", recipes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size); // 페이지 당 항목 수

        return "recipe/list";
    }

    @GetMapping("/recipes/{name}")
    public String getRecipeDetail(@PathVariable("name") String name, Model model) {
        name = UriUtils.decode(name, StandardCharsets.UTF_8);
        Recipe recipe = recipeService.getRecipeByName(name);
        model.addAttribute("recipe", recipe);
        return "recipe/detail";
    }
}
