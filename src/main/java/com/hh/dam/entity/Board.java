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

    @Column(name = "groupId", nullable = false)
    private int groupId;

    @Column(name = "boardName", nullable = false)
    private String boardName;

//    @OneToMany(mappedBy = "board")
//    private List<Post> posts;
}