package com.hh.dam.dto;

import com.hh.dam.entity.Member;
import com.hh.dam.entity.MemberRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SigninRequest {

        @NotBlank(message = "ID를 입력하세요.")
        private String userId;

        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;
        private String passwordCheck;

        @NotBlank(message = "이름을 입력하세요.")
        private String email;

        public Member toEntity(){
            return Member.builder()
                    .userId(userId)
                    .password(password)
                    .email(email)
                    .role(MemberRole.USER)
                    .build();
        }

}
