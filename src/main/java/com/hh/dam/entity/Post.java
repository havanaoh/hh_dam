package com.hh.dam.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne
    @JoinColumn(name = "BoardId", nullable = false)
    private Board board;

    @Column(name = "MemberId", nullable = false)
    private int memberId;

    @Column(name = "BookId", nullable = false)
    private int bookId;

    @Column(name = "PostTitle", nullable = false)
    private String postTitle;

    @Column(name = "PostContent", nullable = false)
    private String postContent;

    @Column(name = "Views", nullable = false, columnDefinition = "int default 0")
    private int views;

    @Column(name = "CreatedDate", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdDate;

    @Column(name = "ModifiedDate")
    private Timestamp modifiedDate;

    @Column(name = "Field")
    private int field;

    @Column(name = "ImageId")
    private int imageId;

    @Column(name = "CommentCount", nullable = false, columnDefinition = "int default 0")
    private int commentCount;

    @Column(name = "plikes", nullable = false, columnDefinition = "int default 0")
    private int plikes;

    @OneToMany(mappedBy = "Post")
    private List<Comment> comments;

}
