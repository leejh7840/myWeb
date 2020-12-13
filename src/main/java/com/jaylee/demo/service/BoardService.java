package com.jaylee.demo.service;

import com.jaylee.demo.model.Board;
import com.jaylee.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board save(Board board){
        return boardRepository.save(board);
    }

}
