package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.dto.CompetitionDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;

    public void saveCompetition(CompetitionDto competitionDto){
        Competition savedCompetition = competitionRepository.save(competitionDto.to());
        /*savedCompetition.ifPresentOrElse(c -> log.info(c.getCompetition_name() + "경기가 등록되었습니다."),
                () -> log.error(competitionDto.getCompetition_name() + "경기 등록에 실패하였습니다."));*/
    }
}
