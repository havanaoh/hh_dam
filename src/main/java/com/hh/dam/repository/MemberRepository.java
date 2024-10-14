package com.hh.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hh.dam.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByLoginId(String UserId);
    
    Member findByLoginId(String UserId);
}