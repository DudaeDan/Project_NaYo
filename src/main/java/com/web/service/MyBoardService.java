package com.web.service;

import com.web.domain.Board;
import java.util.List;

public interface MyBoardService {
    List<Board> findPostsByUserNumber(Long userNumber);


}
