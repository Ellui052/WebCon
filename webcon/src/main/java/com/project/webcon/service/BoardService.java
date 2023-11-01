package com.project.webcon.service;

import com.project.webcon.entity.Board;
import com.project.webcon.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board) {
        boardRepository.save(board);
    }

    public List<Board> list() {
        return boardRepository.findAllByOrderByIdDesc();
    }

    public Board view(int id) {
        return boardRepository.findById(id).get();
    }
}
