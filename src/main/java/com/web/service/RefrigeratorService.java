package com.web.service;

import java.util.List;

import com.web.domain.RefrigeratorIngredient;

public interface RefrigeratorService {

	List<RefrigeratorIngredient> getAllCategory();
	List<RefrigeratorIngredient> getCategory(String category);
	
}
