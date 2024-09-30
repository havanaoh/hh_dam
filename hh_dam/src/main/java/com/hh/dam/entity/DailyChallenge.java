package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DailyChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long daily_id;
    private int member_id;
    private int library_id;
    private int target_pages;
    private int actual_pages;
    private LocalDate challenge_date;
    private Boolean is_success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_challenge_id")
    private MonthlyChallenge monthlyChallenge; // MonthlyChallenge와의 연관 관계



}
