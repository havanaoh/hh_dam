package com.hh.dam.service;

import java.util.Optional;

import com.hh.dam.dto.SignInRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hh.dam.dto.SignUpRequest;
import com.hh.dam.entity.Member;
import com.hh.dam.repository.MemberRepository;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인 ID 중복 확인
    public boolean checkLoginIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    // 로그인 시 비밀번호 비교
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // 회원 로그인 처리
    public Member signIn(SignInRequest signInRequest) {
        Optional<Member> findMember = memberRepository.findByUserId(signInRequest.getUserId());

        if (findMember.isEmpty()) {
            return null;
        }

        Member member = findMember.get();

        if (!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            return null;
        }

        return member;
    }

    // 회원 조회 by userId
    public Member getLoginMemberById(String userId) {
        if (userId == null) return null;

        Optional<Member> findMember = memberRepository.findByUserId(userId);
        return findMember.orElse(null); // Optional에서 값이 없으면 null 반환
    }

    // 회원가입 처리
    public void securitySignUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByUserId(signUpRequest.getUserId())) {
            return;
        }

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        memberRepository.save(signUpRequest.toEntity());
    }

    // 회원 정보 업데이트
    public void updateMember(Member member) {
        memberRepository.save(member); // 회원 정보를 업데이트
    }

    // 비밀번호 암호화 처리
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword); // 비밀번호 암호화
    }
}
