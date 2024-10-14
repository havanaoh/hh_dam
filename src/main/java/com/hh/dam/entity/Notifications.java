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

//    @ManyToOne
//    Member member;
//
//    private String message;
//    private LocalDate createdAt;
//    @Column(columnDefinition = "default false")
//    private Boolean is_read;

}
