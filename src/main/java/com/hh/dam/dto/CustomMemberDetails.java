package com.hh.dam.dto;

import com.hh.dam.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomMemberDetails implements UserDetails {

    private final Member member;

    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    // 사용자의 역할(Role)을 기반으로 권한을 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        return authorities;
    } // 사용자의 역할(Role)을 기반으로 권한을 설정

    // user의 비밀번호 반환, 로그인 검증 시 사용
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // user의 username 반환
    @Override
    public String getUsername() {
        return member.getUserId();
    }

    // 사용자 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
