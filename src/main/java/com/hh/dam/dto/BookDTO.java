package com.hh.dam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    // SubInfo 객체 추가
    private SubInfo subInfo; // SubInfo 유지

    // SubInfo 내부 클래스
    @Data
    public static class SubInfo {
        private String subTitle;
        private String originalTitle;
        @JsonProperty("itemPage") // itemPage를 JSON 프로퍼티로 매핑
        private int itemPage;
    }
}
