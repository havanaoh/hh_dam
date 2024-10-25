package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "PostId", nullable = false)
    private Post post;

    @Column(name = "BoardId", nullable = false)
    private int boardId;

    @Column(name = "MemberId", nullable = false)
    private int memberId;

    @Column(name = "CreatedDate", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdDate;

}
