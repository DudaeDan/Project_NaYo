package com.web.service;

import com.web.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<Board> getBoardList(Pageable pageable);
}
