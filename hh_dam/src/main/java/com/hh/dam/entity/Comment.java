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
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(name = "boardId", nullable = false)
    private int boardId;

    @Column(name = "memberId", nullable = false)
    private int memberId;

    @Column(name = "createdDate", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdDate;

}
