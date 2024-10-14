package com.hh.dam.entity;

public enum BookStatus {
    읽는중("읽는 중"),
    찜("찜"),
    완독("완독"),
    안읽음("안 읽음");

    private final String displayName; // 상태의 표시 이름 저장할 필드

    // 생성자
    BookStatus(String displayName) {
        this.displayName = displayName;
    }

    // Getter: 한글 이름 반환
    public String getDisplayName() {
        return displayName;
    }

}
