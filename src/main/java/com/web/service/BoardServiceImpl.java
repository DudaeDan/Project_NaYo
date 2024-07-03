package com.web.service;

import com.web.domain.Board;
import com.web.repository.BoardRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    
    @Override
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    
    
}
