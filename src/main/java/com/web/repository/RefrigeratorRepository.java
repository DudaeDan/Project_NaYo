package com.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.domain.Ingredient;
import com.web.domain.Refrigerator;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {

	Page<Refrigerator> findByUserNumberOrderByExpDateAsc(Pageable pageable, Long userNumber );
	
	@Query("SELECT ri.refIngredientName FROM Refrigerator r JOIN r.refrigeratorIngredient ri WHERE r.user.userNumber = :userNumber")
    List<String> findIngredientNamesByUserNumber(Long userNumber);
    List<Refrigerator> findByUser_UserNumber(Long userNumber);
    void deleteByUser_UserNumber(Long userNumber);

}
