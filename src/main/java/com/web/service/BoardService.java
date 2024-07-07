package com.web.service;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.Step;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Page<Board> findAllBoards(Pageable pageable); // 새로운 메서드 추가
    
    Board saveBoard(Board board);

    List<Board> findAllBoards();

    Board findBoardById(Long id);

    void deleteBoard(Long id);

    Like saveLike(Like like);

    void deleteLike(Long id);

    Step saveStep(Step step);

    void deleteStep(Long id);

    List<Step> findStepsByBoard(Board board);
    
    void saveBoard(Board board, MultipartFile mainImgFile, List<String> stepDescriptions, List<MultipartFile> stepImages);
    
    void save(Board board);


}