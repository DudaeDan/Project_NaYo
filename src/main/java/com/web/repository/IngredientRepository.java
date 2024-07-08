package com.web.repository;

import com.web.domain.Board;
import com.web.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByBoard(Board board);
    void deleteByBoard(Board board);
}