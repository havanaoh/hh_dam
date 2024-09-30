package com.hh.dam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

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

    @Column(name = "Join_date", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp joinDate;

    @Column(name = "Last_login")
    private Timestamp lastLogin;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "Report_time")
    private int reportTime;

    @Column(name = "Report_reason")
    private String reportReason;

    @Column(name = "Image_id")
    private int imageId;

}

