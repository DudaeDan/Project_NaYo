package com.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.domain.Ingredient;
import com.web.domain.Refrigerator;
import com.web.domain.RefrigeratorIngredient;
import com.web.domain.SearchRecipe;

public interface RefrigeratorService {

	// 냉장고 메인
	Page<Refrigerator> getRefriList(Pageable pageable, Long userNumber);

	// 재료 삭제
	void ingredientDelete(List<Long> refrigeratorNumbers);

	// 재료 모든 카테고리
	List<RefrigeratorIngredient> getAllCategory();

	// 재료 각 카테고리
	List<RefrigeratorIngredient> getCategory(String category);

	// 재료 추가
	void ingredientAddConfirm(List<Refrigerator> refrigerators);

	//재료 이름 가져오기
	List<String> getIngreName(Long userNumber);

	// 레시피 검색
	List<SearchRecipe> getRecipe(List<String> ingre);
}
