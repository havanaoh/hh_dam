package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.print.Book;
import java.time.LocalDate;

@Entity
@Data
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int library_id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false, unique = true) // 유일한 관계 설정
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // Foreign Key, Book ID
    private Book book;
    private LocalDate start_date;
    private LocalDate end_date;
    private int current_page;

}
