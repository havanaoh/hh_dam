package com.hh.dam.controller;

import com.hh.dam.entity.Board;
import com.hh.dam.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 모든 게시판 조회
    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 특정 그룹 ID에 속하는 게시판 조회
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Board>> getBoardsByGroupId(@PathVariable int groupId) {
        List<Board> boards = boardService.getBoardsByGroupId(groupId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 게시판 ID로 특정 게시판 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<Board> getBoardById(@PathVariable int boardId) {
        Optional<Board> board = boardService.getBoardById(boardId);
        return board.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 새로운 게시판 추가
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board createdBoard = boardService.saveBoard(board);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    // 게시판 정보 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable int boardId, @RequestBody Board boardDetails) {
        Optional<Board> board = boardService.getBoardById(boardId);
        if (board.isPresent()) {
            Board existingBoard = board.get();
            existingBoard.setBoardName(boardDetails.getBoardName());
            existingBoard.setGroupId(boardDetails.getGroupId());
            Board updatedBoard = boardService.updateBoard(existingBoard);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 게시판 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
