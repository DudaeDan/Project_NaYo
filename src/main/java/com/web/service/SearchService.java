package com.web.service;

import java.util.List;

import com.web.domain.Board;

public interface SearchService {

	List<Board> search(String type, String keyword);



}
