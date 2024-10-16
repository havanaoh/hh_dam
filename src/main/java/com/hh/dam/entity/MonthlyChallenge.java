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
    private long monthlyId;
    private int memberId;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "monthlyChallenge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DailyChallenge> dailyChallenges; // DailyChallenge와의 관계 설정

    protected MonthlyChallenge() {
    }

    // 인자를 받는 생성자
    public MonthlyChallenge(int memberId) {
        this.memberId = memberId;
        LocalDate now = LocalDate.now();
        this.startDate = now.withDayOfMonth(1);
        YearMonth yearMonth = YearMonth.from(now);
        this.endDate = yearMonth.atEndOfMonth();
    }

}
