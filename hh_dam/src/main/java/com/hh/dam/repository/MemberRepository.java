package com.hh.dam.repository;

public interface MemberRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}