package com.hh.dam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class MonthlyChallenge {
    @Id
    private String monthly_id;
    private String member_id;
    private Date start_date;
    private Date end_date;
    private int total_days;
    private int success_days;
    private float success_rate;

    @Enumerated(EnumType.STRING)  // ENUM_TYPE을 STRING으로 설정
    private ChallengeStatus status;

}
