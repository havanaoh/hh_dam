package com.hh.dam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId") // 명확히 memberId와 매핑
    private Member member;

    private String message;
    private LocalDate createdAt;

    @Column(columnDefinition = "boolean default false")
    private Boolean isRead;

}
