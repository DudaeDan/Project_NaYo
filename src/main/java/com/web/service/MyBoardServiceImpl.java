package com.web.service;

import com.web.domain.Board;
import com.web.repository.MyBoardRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MyBoardServiceImpl implements MyBoardService {

    @Autowired
    private MyBoardRepository myBoardRepository;

    @Override
    public List<Board> findPostsByUserNumber(Long userNumber) {
        return myBoardRepository.findByUser_UserNumber(userNumber);
    }
    
   
 
    
    
}
