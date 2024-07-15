package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.Board;
import com.web.repository.BoardRepository;

@Service
public class SearchServiceImpl implements SearchService {
	
//	  
//	@Autowired
//	private BoardRepository boardRepository;
//	    @Override
//	    public List<Board> search(String type, String keyword) {
//	        if (type.equals("title")) {
//	            return boardRepository.findByBoardTitleContaining(keyword);
//	        } else if (type.equals("content")) {
//	            return boardRepository.findByBoardContentContaining(keyword);
//	        }
//	        return null;
//	    }

}