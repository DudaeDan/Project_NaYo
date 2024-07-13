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

    // RecipeService를 주입받아 초기화
    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    
    // 레시피 목록을 가져와서 뷰에 전달하는 메서드
    @GetMapping("/recipes")
    public String getRecipes(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "8") int size) {
        // 현재 페이지와 페이지 크기에 따라 레시피 목록을 가져옴
        List<Recipe> recipes = recipeService.fetchRecipes((page - 1) * size + 1, page * size);
        // 전체 레시피 개수를 가져옴
        int totalRecipes = recipeService.getTotalCount();
        // 전체 페이지 수를 계산
        int totalPages = (int) Math.ceil((double) totalRecipes / size);

        // URL 인코딩된 레시피 이름 추가
        for (Recipe recipe : recipes) {
            String encodedName = UriUtils.encode(recipe.getRCP_NM(), StandardCharsets.UTF_8);
            recipe.setEncodedName(encodedName);
        }

        // 모델에 데이터를 추가하여 뷰에 전달
        model.addAttribute("recipes", recipes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size); // 페이지 당 항목 수

        // 레시피 목록 뷰를 반환
        return "recipe/list";
    }

    // 특정 레시피의 세부 정보를 가져와서 뷰에 전달하는 메서드
    @GetMapping("/recipes/{name}")
    public String getRecipeDetail(@PathVariable("name") String name, Model model) {
        // URL 디코딩된 레시피 이름
        name = UriUtils.decode(name, StandardCharsets.UTF_8);
        // 레시피 이름으로 레시피를 가져옴
        Recipe recipe = recipeService.getRecipeByName(name);
        // 모델에 데이터를 추가하여 뷰에 전달
        model.addAttribute("recipe", recipe);
        // 레시피 세부 정보 뷰를 반환
        return "recipe/detail";
    }
}
