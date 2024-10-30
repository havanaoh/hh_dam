package com.hh.dam.repository;

import com.hh.dam.entity.MonthlyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyChallengeRepository extends JpaRepository <MonthlyChallenge, Long> {


}
