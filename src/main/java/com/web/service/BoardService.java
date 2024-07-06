package com.web.service;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.Step;
import com.web.domain.Ingredient;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Page<Board> findAllBoards(Pageable pageable);

    Board saveBoard(Board board);

    List<Board> findAllBoards();

    Board findBoardById(Long id);

    void deleteBoard(Long id);

    Like saveLike(Like like);

    void deleteLike(Long id);

    Step saveStep(Step step);

    void deleteStep(Long id);

    List<Step> findStepsByBoard(Board board);

    void saveBoard(Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages, List<String> ingredientNames, List<String> ingredientAmounts);

    void save(Board board);

    List<Ingredient> findIngredientsByBoard(Board board);

    Ingredient saveIngredient(Ingredient ingredient);

    void deleteIngredient(Long id);

    void deleteBoardWithFiles(Long id);

    void updateBoard(Long id, Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages, List<String> ingredientNames, List<String> ingredientAmounts);
}