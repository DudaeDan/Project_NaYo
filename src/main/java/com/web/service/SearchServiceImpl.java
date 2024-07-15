package com.web.service;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.Board;
import com.web.repository.BoardRepository;
=======
import com.web.domain.Board;
import com.web.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
>>>>>>> 2442d04db1f51ce1e0601b54b16aa6c11fc816f7

@Service
public class SearchServiceImpl implements SearchService {
	
	  
	@Autowired
	private BoardRepository boardRepository;
	    @Override
	    public List<Board> search(String type, String keyword) {
	        if (type.equals("title")) {
	            return boardRepository.findByBoardTitleContaining(keyword);
	        } else if (type.equals("content")) {
	            return boardRepository.findByBoardContentContaining(keyword);
	        }
	        return null;
	    }

<<<<<<< HEAD
}
=======
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
>>>>>>> 2442d04db1f51ce1e0601b54b16aa6c11fc816f7
