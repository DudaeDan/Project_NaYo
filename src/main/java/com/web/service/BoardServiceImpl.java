package com.web.service;

import com.web.domain.Board;
import com.web.domain.CommentLike;
import com.web.domain.Comments;
import com.web.domain.Like;
import com.web.domain.Reply;
import com.web.domain.ReplyLike;
import com.web.domain.Step;
import com.web.domain.Ingredient;
import com.web.repository.BoardRepository;
import com.web.repository.CommentLikeRepository;
import com.web.repository.CommentRepository;
import com.web.repository.LikeRepository;
import com.web.repository.ReplyLikeRepository;
import com.web.repository.ReplyRepository;
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

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    
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
    public void updateBoard(Long id, Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages, List<String> ingredientNames, List<String> ingredientAmounts, String existingMainImg, List<String> existingStepImages) {
        Board existingBoard = boardRepository.findById(id).orElse(null);
        if (existingBoard != null) {
            existingBoard.setBoardTitle(board.getBoardTitle());
            existingBoard.setBoardContent(board.getBoardContent());

            if (mainImgFile != null && !mainImgFile.isEmpty()) {
                fileService.deleteFile(existingBoard.getMainImg(), MAIN_IMG_DIR);
                String mainImgName = fileService.saveFile(mainImgFile, MAIN_IMG_DIR);
                existingBoard.setMainImg(mainImgName);
            } else {
                existingBoard.setMainImg(existingMainImg);
            }

            // Steps 업데이트
            List<Step> steps = existingBoard.getSteps();
            steps.clear();
            for (int i = 0; i < stepDescriptions.size(); i++) {
                Step step = new Step();
                step.setStepNumber(i + 1);
                step.setStepDescription(stepDescriptions.get(i));

                if (i < stepImages.size() && stepImages.get(i) != null && !stepImages.get(i).isEmpty()) {
                    MultipartFile stepImg = stepImages.get(i);
                    String stepImgName = fileService.saveFile(stepImg, STEP_IMG_DIR);
                    step.setStepImage(stepImgName);
                } else {
                    step.setStepImage(existingStepImages.get(i));
                }
                step.setBoard(existingBoard);
                steps.add(step);
            }
            existingBoard.setSteps(steps);

            // Ingredients 업데이트
            List<Ingredient> ingredients = existingBoard.getIngredients();
            ingredients.clear();
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
            // 이미지 파일 삭제
            fileService.deleteFile(board.getMainImg(), MAIN_IMG_DIR);
            for (Step step : board.getSteps()) {
                fileService.deleteFile(step.getStepImage(), STEP_IMG_DIR);
            }

            // 댓글 및 관련된 답글, 좋아요 삭제
            for (Comments comment : board.getComments()) {
                // 답글 및 답글 좋아요 삭제
                for (Reply reply : comment.getReplies()) {
                    // 답글 좋아요 삭제
                    List<ReplyLike> replyLikes = replyLikeRepository.findByReply(reply);
                    for (ReplyLike replyLike : replyLikes) {
                        replyLikeRepository.delete(replyLike);
                    }
                    // 답글 삭제
                    replyRepository.delete(reply);
                }

                // 댓글 좋아요 삭제
                List<CommentLike> commentLikes = commentLikeRepository.findByComment(comment);
                for (CommentLike commentLike : commentLikes) {
                    commentLikeRepository.delete(commentLike);
                }

                // 댓글 삭제
                commentRepository.delete(comment);
            }

            // 보드 삭제
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