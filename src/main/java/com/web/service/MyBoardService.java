package com.web.service;

import com.web.domain.Board;
import com.web.domain.User;

import java.util.List;

public interface MyBoardService {
    List<Board> findPostsByUserNumber(Long userNumber);

	void updateUser(User user);


}
