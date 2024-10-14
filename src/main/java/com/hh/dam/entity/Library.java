package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int libraryId;
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false, unique = true) // 유일한 관계 설정
    private Member member;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // Foreign Key, Book ID
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentPage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status; // 책 상태 (READING, WISHLIST, COMPLETED, NOT_READ)
}
