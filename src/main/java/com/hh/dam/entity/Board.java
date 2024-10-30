package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    @Column(name = "BoardName", nullable = false, length = 20)
    private String boardName;

    @Column(name = "GroupId")
    private int groupId;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;


}