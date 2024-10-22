package com.hh.dam.service;

import com.hh.dam.dto.CustomMemberDetails;
import com.hh.dam.entity.Member;
import com.hh.dam.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 로그 출력: userId가 제대로 전달되고 있는지 확인
        System.out.println("Attempting to load user with userId: " + userId);

        // 데이터베이스에서 사용자 조회
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);

        // 사용자가 존재하는지 여부 확인 및 로그 출력
        if (memberOpt.isPresent()) {
            System.out.println("User found: " + memberOpt.get());
            return new CustomMemberDetails(memberOpt.get());
        } else {
            System.out.println("User not found with userId: " + userId);
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
    }
}
