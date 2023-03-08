package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.dto.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;

    public void saveCompetition(CompetitionRequestDto competitionDto){
        try {
            competitionRepository.save(competitionDto.to());
            log.info(competitionDto.getCompetitionName() + " 경기가 등록되었습니다.");
        } catch (Exception e) {
            log.error(competitionDto.getCompetitionName() + " 경기 등록에 실패하였습니다.");
        }
    }
}
