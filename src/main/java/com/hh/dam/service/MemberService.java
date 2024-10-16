package com.hh.dam.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.dam.dto.SignInRequest;
import com.hh.dam.dto.SignUpRequest;
import com.hh.dam.entity.Member;
import com.hh.dam.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean checkLoginIdDuplicate(String UserId){
        return memberRepository.existsByUserId(UserId);
    }


    public void signup(SignUpRequest signupRequest) {
        memberRepository.save(signupRequest.toEntity());
    }

    public Member signin(SignInRequest signinRequest) {
        Member findMember = memberRepository.findByUserId(signinRequest.getUserId());

        if(findMember == null){
            return null;
        }

        if (!findMember.getPassword().equals(signinRequest.getPassword())) {
            return null;
        }

        return findMember;
    }

    public Member getLoginMemberById(Integer memberId){
        if(memberId == null) return null;

        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);

    }
}
