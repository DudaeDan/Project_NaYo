package com.web.service;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.Step;
import com.web.repository.BoardRepository;
import com.web.repository.LikeRepository;
import com.web.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	public void saveBoard(Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages) {
	    // 조회수와 좋아요 수 초기화
	    board.setBoardHit(0);
	    board.setBoardLike(0);
	
	    // Save main image
	    if (!mainImgFile.isEmpty()) {
	        String mainImgName = saveFile(mainImgFile, MAIN_IMG_DIR);
	        board.setMainImg(mainImgName); // 파일 경로를 엔티티에 설정
	    }
	
	    // Save steps
	    List<Step> steps = new ArrayList<>();
	    for (int i = 0; i < stepDescriptions.size(); i++) {
	        Step step = new Step();
	        step.setStepNumber(i + 1); // 스텝 번호 설정 (1부터 시작)
	        step.setStepDescription(stepDescriptions.get(i));
	
	        if (i < stepImages.size()) {
	            MultipartFile stepImg = stepImages.get(i);
	            if (stepImg != null && !stepImg.isEmpty()) {
	                String stepImgName = saveFile(stepImg, STEP_IMG_DIR);
	                step.setStepImage(stepImgName); // 파일 경로를 엔티티에 설정
	            }
	        }
	        step.setBoard(board);
	        steps.add(step);
	    }
	
	    board.setSteps(steps);
	    boardRepository.save(board);
	}

    private String saveFile(MultipartFile file, String directory) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(directory + UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
            Files.write(path, bytes);
            return path.getFileName().toString(); // 파일 경로 반환
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

      
}