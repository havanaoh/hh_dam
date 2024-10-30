package com.hh.dam.service;

import com.hh.dam.entity.Board;
import com.hh.dam.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 모든 게시판 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 특정 그룹 ID에 속하는 게시판 조회
    public List<Board> getBoardsByGroupId(int groupId) {
        return boardRepository.findByGroupId(groupId);
    }

    // 게시판 ID로 특정 게시판 조회
    public Optional<Board> getBoardById(int boardId) {
        return boardRepository.findById(boardId);
    }

    // 새로운 게시판 추가
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    // 게시판 정보 수정
    public Board updateBoard(Board board) {
        return boardRepository.save(board);
    }

    // 게시판 삭제
    public void deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
    }
}
