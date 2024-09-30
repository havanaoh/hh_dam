package com.hh.dam.service;

import com.hh.dam.entity.DailyChallenge;
import com.hh.dam.repository.DailyChallengeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;

    public DailyChallengeService(DailyChallengeRepository dailyChallengeRepository) {
        this.dailyChallengeRepository = dailyChallengeRepository;
    }

    @Transactional
    public void updateDailyChallengeStatus(Long dailyChallengeId) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.findById(dailyChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("챌린지를 찾을 수 없습니다."));
        // 목표 페이지 달성 여부 업데이트
        dailyChallenge.setIs_success(dailyChallenge.getActual_pages() >= dailyChallenge.getTarget_pages());
        // 저장
        dailyChallengeRepository.save(dailyChallenge);
    }

}
