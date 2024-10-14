package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DailyChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dailyId;
    private int memberId;
    private int libraryId;
    private int targetPages;
    private int actualPages;
    private LocalDate challengeDate;
    private Boolean is_success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_challenge_id")
    private MonthlyChallenge monthlyChallenge; // MonthlyChallenge와의 연관 관계



}
