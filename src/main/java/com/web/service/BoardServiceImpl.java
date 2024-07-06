package com.web.service;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.Step;
import com.web.domain.Ingredient;
import com.web.repository.BoardRepository;
import com.web.repository.LikeRepository;
import com.web.repository.StepRepository;
import com.web.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private static final String MAIN_IMG_DIR = "src/main/resources/static/Images/border/main/";
    private static final String STEP_IMG_DIR = "src/main/resources/static/Images/border/step/";

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Step saveStep(Step step) {
        return stepRepository.save(step);
    }

    @Override
    public void deleteStep(Long id) {
        stepRepository.deleteById(id);
    }

    @Override
    public List<Step> findStepsByBoard(Board board) {
        return stepRepository.findByBoard(board);
    }

    @Override
    public List<Ingredient> findIngredientsByBoard(Board board) {
        return ingredientRepository.findByBoard(board);
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveBoard(Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages, List<String> ingredientNames, List<String> ingredientAmounts) {
        board.setBoardHit(0);
        board.setBoardLike(0);

        if (!mainImgFile.isEmpty()) {
            String mainImgName = fileService.saveFile(mainImgFile, MAIN_IMG_DIR);
            board.setMainImg(mainImgName);
        }

        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < stepDescriptions.size(); i++) {
            Step step = new Step();
            step.setStepNumber(i + 1);
            step.setStepDescription(stepDescriptions.get(i));

            if (i < stepImages.size()) {
                MultipartFile stepImg = stepImages.get(i);
                if (stepImg != null && !stepImg.isEmpty()) {
                    String stepImgName = fileService.saveFile(stepImg, STEP_IMG_DIR);
                    step.setStepImage(stepImgName);
                }
            }
            step.setBoard(board);
            steps.add(step);
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientNames.size(); i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientNames.get(i));
            ingredient.setAmount(ingredientAmounts.get(i));
            ingredient.setBoard(board);
            ingredients.add(ingredient);
        }

        board.setSteps(steps);
        board.setIngredients(ingredients);
        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void updateBoard(Long id, Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages, List<String> ingredientNames, List<String> ingredientAmounts) {
        Board existingBoard = boardRepository.findById(id).orElse(null);
        if (existingBoard != null) {
            existingBoard.setBoardTitle(board.getBoardTitle());
            existingBoard.setBoardContent(board.getBoardContent());

            if (!mainImgFile.isEmpty()) {
                fileService.deleteFile(existingBoard.getMainImg(), MAIN_IMG_DIR);
                String mainImgName = fileService.saveFile(mainImgFile, MAIN_IMG_DIR);
                existingBoard.setMainImg(mainImgName);
            }

            // Steps 업데이트
            List<Step> existingSteps = existingBoard.getSteps();
            if (existingSteps != null) {
                for (Step step : existingSteps) {
                    stepRepository.delete(step);
                }
            }

            List<Step> steps = new ArrayList<>();
            for (int i = 0; i < stepDescriptions.size(); i++) {
                Step step = new Step();
                step.setStepNumber(i + 1);
                step.setStepDescription(stepDescriptions.get(i));

                if (i < stepImages.size()) {
                    MultipartFile stepImg = stepImages.get(i);
                    if (stepImg != null && !stepImg.isEmpty()) {
                        String stepImgName = fileService.saveFile(stepImg, STEP_IMG_DIR);
                        step.setStepImage(stepImgName);
                    } else {
                        // 새로운 이미지가 없으면 기존 이미지를 유지
                        Step existingStep = existingBoard.getSteps().size() > i ? existingBoard.getSteps().get(i) : null;
                        if (existingStep != null) {
                            step.setStepImage(existingStep.getStepImage());
                        }
                    }
                }
                step.setBoard(existingBoard);
                steps.add(step);
            }
            existingBoard.setSteps(steps);

            // Ingredients 업데이트
            List<Ingredient> existingIngredients = existingBoard.getIngredients();
            if (existingIngredients != null) {
                for (Ingredient ingredient : existingIngredients) {
                    ingredientRepository.delete(ingredient);
                }
            }

            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientNames.size(); i++) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientNames.get(i));
                ingredient.setAmount(ingredientAmounts.get(i));
                ingredient.setBoard(existingBoard);
                ingredients.add(ingredient);
            }
            existingBoard.setIngredients(ingredients);

            boardRepository.save(existingBoard);
        }
    }

    @Override
    @Transactional
    public void deleteBoardWithFiles(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board != null) {
            fileService.deleteFile(board.getMainImg(), MAIN_IMG_DIR);
            for (Step step : board.getSteps()) {
                fileService.deleteFile(step.getStepImage(), STEP_IMG_DIR);
            }
            boardRepository.delete(board);
        }
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public Page<Board> findAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}