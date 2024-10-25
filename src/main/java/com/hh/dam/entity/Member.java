package com.hh.dam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "UserID", nullable = false, unique = true)
    private String userId;

    @Column(name = "Password", nullable = false)
    private String password;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Nickname", nullable = false, unique = true, length = 18)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false, columnDefinition = "varchar(255) default 'USER'")
    private MemberRole role;

    @Column(name = "JoinDate", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp joinDate;

    @Column(name = "LastLogin")
    private Timestamp lastLogin;

    @Column(name = "IsActive", columnDefinition = "boolean default true") //휴면 여부
    private boolean isActive;

    @Column(name = "ReportTime")
    private int reportTime;

    @Column(name = "ReportReason")
    private String reportReason;

    @Column(name = "ImageId")
    private int imageId;

    @PrePersist
    protected void onCreate() {
        this.joinDate = new Timestamp(System.currentTimeMillis());
        this.isActive = true; // 기본값 true 설정
    }

}

