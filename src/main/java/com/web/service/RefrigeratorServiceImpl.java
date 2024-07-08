package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.RefrigeratorIngredient;
import com.web.repository.RefrigeratorIngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefrigeratorServiceImpl implements RefrigeratorService{

	@Autowired
	RefrigeratorIngredientRepository refIngreRepo;
	
	
	@Override
	public List<RefrigeratorIngredient> getAllCategory() {
		return refIngreRepo.findAll();
	}
	
	@Override
	public List<RefrigeratorIngredient> getCategory(String category) {
		return refIngreRepo.findByRefIngredientCategoryContaining(category);
	}
	
}
