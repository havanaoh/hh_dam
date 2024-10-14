package com.hh.dam.service;

import com.hh.dam.entity.DailyChallenge;
import com.hh.dam.entity.MonthlyChallenge;
import com.hh.dam.repository.MonthlyChallengeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class MonthlyChallengeService {

    private final MonthlyChallengeRepository monthlyChallengeRepository;
    private final DailyChallengeService dailyChallengeService;

    // 생성자에 DailyChallengeService 추가
    public MonthlyChallengeService(MonthlyChallengeRepository monthlyChallengeRepository,
                                   DailyChallengeService dailyChallengeService) {
        this.monthlyChallengeRepository = monthlyChallengeRepository;
        this.dailyChallengeService = dailyChallengeService; // 초기화
    }

    @Transactional
    public String updateMonthlyChallengeStatus(Long monthlyChallengeId) {
        MonthlyChallenge monthlyChallenge = monthlyChallengeRepository.findById(monthlyChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("챌린지를 찾을 수 없습니다."));

        // 각 DailyChallenge의 성공 상태 업데이트
        for (DailyChallenge dailyChallenge : monthlyChallenge.getDailyChallenges()) {
            dailyChallengeService.updateDailyChallengeStatus(dailyChallenge.getDailyId());
        }

        // 현재 날짜 가져오기
        LocalDate now = LocalDate.now();

        // 상태 계산
        if (now.isBefore(monthlyChallenge.getStartDate()) || now.isAfter(monthlyChallenge.getEndDate())) {
            return "미진행";  // 시작일 이전 또는 종료일 이후에는 미진행 상태
        }

        if (now.getDayOfMonth() <= 10) {
            return "진행중";  // 1~10일: 진행중
        } else {
            int totalDays = monthlyChallenge.getDailyChallenges().size();
            int successDays = (int) monthlyChallenge.getDailyChallenges().stream()
                    .filter(DailyChallenge::getIs_success).count();
            float successRate = totalDays > 0 ? (float) successDays / totalDays * 100 : 0;

            // 상태 결정
            if (successRate >= 90) {
                return "골드";
            } else if (successRate >= 60) {
                return "실버";
            } else if (successRate >= 30) {
                return "브론즈";
            } else {
                return "실패";
            }
        }
    }


}