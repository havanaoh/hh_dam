package com.hh.dam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    private String book_title;
    private String author;
    private String publicher;
    private int book_cover;
    private String isbn;
    private int total_page;
    private Timestamp book_created;
    private Timestamp book_modified;

}
