package com.hh.dam.dto;

import com.hh.dam.entity.Member;
import com.hh.dam.entity.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "ID를 입력하세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;

    public Member toEntity(String encodedPassword){
        return Member.builder()
                .userId(this.userId)
                .password(encodedPassword)
                .email(this.email)
                .role(MemberRole.USER)
                .nickname(this.nickname)
                .joinDate(new Timestamp(System.currentTimeMillis()))  // 현재 시간으로 joinDate 설정
                .build();
    }
}
