package com.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.domain.Ingredient;
import com.web.domain.Refrigerator;
import com.web.domain.RefrigeratorIngredient;
import com.web.domain.SearchRecipe;
import com.web.repository.RefrigeratorIngredientRepository;
import com.web.repository.RefrigeratorRepository;
import com.web.repository.SearchRecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefrigeratorServiceImpl implements RefrigeratorService {

	@Autowired
	RefrigeratorIngredientRepository refIngreRepo;
	@Autowired
	RefrigeratorRepository refriRepo;
	@Autowired
	SearchRecipeRepository SRRepo;

	@Override
	public Page<Refrigerator> getRefriList(Pageable pageable, Long userNumber) {
		return refriRepo.findByUserNumberOrderByExpDateAsc(pageable, userNumber);
	}

	@Override
	public void ingredientDelete(List<Long> refrigeratorNumbers) {
		for (Long number : refrigeratorNumbers) {
			refriRepo.deleteById(number);
		}
	}

	@Override
	public List<RefrigeratorIngredient> getAllCategory() {
		return refIngreRepo.findAll();
	}

	@Override
	public List<RefrigeratorIngredient> getCategory(String category) {
		return refIngreRepo.findByRefIngredientCategoryContaining(category);
	}

	@Override
	public void ingredientAddConfirm(List<Refrigerator> refrigerators) {
		refriRepo.saveAll(refrigerators);
	}

	@Override
	public List<String> getIngreName(Long userNumber) {
		return refriRepo.findIngredientNamesByUserNumber(userNumber);
	}

	@Override
	public List<SearchRecipe> getRecipe(List<String> ingre) {
		String pattern = makePattern(ingre);
		return SRRepo.findByIngredientPattern(pattern);
	}

	private String makePattern(List<String> ingre) {
		String regexPattern = String.join("|", ingre);
		return regexPattern;
	}

}
