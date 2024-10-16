package com.hh.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hh.dam.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByUserId(String UserId);
    
    Member findByUserId(String UserId);
}