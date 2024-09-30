package com.hh.dam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DailyChallenge {
    @Id
    private int daily_id;
    private int member_id;
    private int library_id;
    private int target_pages;
    private int actual_pages;
    private String challenge_date;
    private Boolean is_success;

}
