package com.hh.dam.config;

import com.hh.dam.entity.Board;
import com.hh.dam.service.BoardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BoardInitializer {

    @Bean
    public CommandLineRunner initializeBoards(BoardService boardService) {
        return args -> {
            List<Board> defaultBoards = Arrays.asList(
                    new Board(0, 1, "소설"),
                    new Board(0, 2, "에세이"),
                    new Board(0, 3, "만화"),
                    new Board(0, 4, "시희곡"),
                    new Board(0, 5, "대중문학")
            );

            for (Board board : defaultBoards) {
                // groupId로 이미 존재하는 게시판인지 확인
                if (boardService.getBoardsByGroupId(board.getGroupId()).isEmpty()) {
                    boardService.saveBoard(board);
                }
            }
        };
    }
}
