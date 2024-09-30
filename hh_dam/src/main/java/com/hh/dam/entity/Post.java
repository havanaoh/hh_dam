package com.hh.dam.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(name = "boardId", nullable = false)
    private int boardId;

    @Column(name = "MmemberId", nullable = false)
    private int memberId;

    @Column(name = "bookId", nullable = false)
    private int bookId;

    @Column(name = "postTitle", nullable = false)
    private String postTitle;

    @Column(name = "postContent", nullable = false)
    private String postContent;

    @Column(name = "views", nullable = false, columnDefinition = "int default 0")
    private int views;

    @Column(name = "createdDate", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdDate;

    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;

    @Column(name = "field")
    private int field;

    @Column(name = "imageId")
    private int imageId;

    @Column(name = "commentCount", nullable = false, columnDefinition = "int default 0")
    private int commentCount;

    @Column(name = "plikes", nullable = false, columnDefinition = "int default 0")
    private int plikes;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments;

}
