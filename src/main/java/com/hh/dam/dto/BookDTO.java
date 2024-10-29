package com.hh.dam.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String title;
    private String author;
    private String publisher;
    private String cover;
    private String isbn;
    private int itemId;
    private int totalPage;
    // 필요한 필드 추가

    // Getters and Setters
}
