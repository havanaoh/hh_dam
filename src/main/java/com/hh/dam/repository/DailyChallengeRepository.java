package com.hh.dam.repository;

import com.hh.dam.entity.DailyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {

}
