package com.hh.dam.service;

import com.hh.dam.entity.Member;
import com.hh.dam.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));  // 예외 처리
    }

    public void registerMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));  // 비밀번호 암호화
        memberRepository.save(member);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);  // 인코딩된 비밀번호와 비교
    }

}
