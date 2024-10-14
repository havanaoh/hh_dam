package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String bookTitle;
    private String author;
    private String publisher;
    private int bookCover;
    private String isbn;
    private int totalPage;
    private Timestamp bookCreated;
    private Timestamp bookModified;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default '안 읽음'")
    private BookStatus bookStatus;

    @OneToMany(mappedBy = "book")
    private List<Library> libraries;

}
