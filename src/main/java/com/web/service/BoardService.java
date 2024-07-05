package com.web.service;

import com.web.domain.Board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<Board> getBoardList(Pageable pageable);

    List<Board> getAllBoards();
}
