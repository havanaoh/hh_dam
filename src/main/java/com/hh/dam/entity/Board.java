package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    @Column(name = "BoardName", nullable = false, length = 20)
    private String boardName;

    @Column(name = "GroupId")
    private int groupId;

    @OneToMany(mappedBy = "board")  // 수정됨
    private List<Post> posts;

    public Board(int boardId, int groupId, String boardName) {
        this.boardId = boardId;
        this.groupId = groupId;
        this.boardName = boardName;
    }
}