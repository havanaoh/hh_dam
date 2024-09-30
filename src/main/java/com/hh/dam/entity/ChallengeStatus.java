package com.hh.dam.entity;

public enum ChallengeStatus {
    진행중("진행중"),
    실패("실패"),
    브론즈("브론즈"),
    실버("실버"),
    골드("골드");

    private final String displayName; // displayName 필드 추가

    // 생성자 정의 (매개변수가 있는 생성자)
    ChallengeStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName; // displayName 반환 메소드 추가
    }

}
