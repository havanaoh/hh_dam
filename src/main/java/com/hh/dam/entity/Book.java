package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String bookTitle;
    private String author;
    private String publisher;
    private String cover;
    private String isbn;
    private int totalPage;
    private Timestamp bookCreated;
    private Timestamp bookModified;


    @OneToMany(mappedBy = "book")
    private List<Library> libraries;

}
