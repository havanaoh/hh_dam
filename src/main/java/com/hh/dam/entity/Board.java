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

    @Column(name = "boardName", nullable = false, length = 20)
    private String boardName;

    @Column(name = "groupId")
    private int groupId;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;


}