package com.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.domain.Refrigerator;
import com.web.domain.RefrigeratorIngredient;

public interface RefrigeratorService {

	List<RefrigeratorIngredient> getAllCategory();

	List<RefrigeratorIngredient> getCategory(String category);

	void ingredientAddConfirm(List<Refrigerator> refrigerators);

	Page<Refrigerator> getRefriList(Pageable pageable,Long userNumber);
	
	void ingredientDelete(List<Long> refrigeratorNumbers);
}
