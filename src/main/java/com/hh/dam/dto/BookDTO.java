package com.hh.dam.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String title;
    private String link;
    private String author;
    private String publisher;
    private String pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private int itemId;
    private int priceSales;
    private int priceStandard;
    private String cover;

    private SubInfo subInfo; // SubInfo 객체 추가

    // Getters and Setters

    @Data
    public static class SubInfo {
        private String subTitle;
        private String originalTitle;
        private int itemPage; // 페이지 수 필드 추가
    }
}