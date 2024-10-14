package com.hh.dam.dto;

import com.hh.dam.entity.Member;
import com.hh.dam.entity.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "ID를 입력하세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    public Member toEntity(){
        return Member.builder()
                .userId(this.userId)
                .password(this.password)
                .email(this.email)
                .role(MemberRole.USER)
                .build();
    }
}
