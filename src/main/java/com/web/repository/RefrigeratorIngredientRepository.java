package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.RefrigeratorIngredient;

public interface RefrigeratorIngredientRepository extends JpaRepository<RefrigeratorIngredient, Long>{

	List<RefrigeratorIngredient> findByRefIngredientCategoryContaining(String category);
}
