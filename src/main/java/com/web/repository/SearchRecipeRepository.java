package com.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.domain.SearchRecipe;

public interface SearchRecipeRepository extends JpaRepository<SearchRecipe, Long> {

	@Query(value = "SELECT * FROM NY_search_recipe WHERE REGEXP_LIKE (recipe_ingredient, :pattern)", nativeQuery = true)
	List<SearchRecipe> findByIngredientPattern(@Param("pattern") String pattern);

}
