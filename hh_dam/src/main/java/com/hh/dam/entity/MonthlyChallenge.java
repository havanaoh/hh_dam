package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Data
@Entity
public class MonthlyChallenge {
    @Id
    private long monthly_id;
    private int member_id;
    private LocalDate start_date;
    private LocalDate end_date;

    @OneToMany(mappedBy = "monthlyChallenge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DailyChallenge> dailyChallenges; // DailyChallenge와의 관계 설정

    // 기본 생성자 (JPA용)
    protected MonthlyChallenge() {
    }

    // 인자를 받는 생성자
    public MonthlyChallenge(int member_id) {
        this.member_id = member_id;
        LocalDate now = LocalDate.now();
        this.start_date = now.withDayOfMonth(1);
        YearMonth yearMonth = YearMonth.from(now);
        this.end_date = yearMonth.atEndOfMonth();
    }
}
