package com.web.service;

import com.web.domain.Board;

import java.util.List;

public interface SearchService {
    List<Board> search(String type, String keyword);
}
