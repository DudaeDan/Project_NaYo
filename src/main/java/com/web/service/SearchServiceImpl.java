package com.web.service;

import com.web.domain.Board;
import com.web.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final BoardRepository boardRepository;

    @Autowired
    public SearchServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> search(String type, String keyword) {
        if (type.equals("title")) {
            return boardRepository.findByBoardTitleContaining(keyword);
        } else if (type.equals("content")) {
            return boardRepository.findByBoardContentContaining(keyword);
        }
        return null;
    }
}
