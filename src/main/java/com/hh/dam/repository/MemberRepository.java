package com.hh.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hh.dam.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByUserId(String memberId);

    Optional<Member> findByUserId(String memberId);
}