package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CmptEventResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionEventService {

    private final CompetitionEventRepository competitionEventRepository;
    private final CompetitionRepository competitionRepository;


    public List<CmptEventResponseDto> findCompetitionEventDataByCompetitionId(Long competitionId) {
        Competition competitionData = competitionRepository.findByCompetitionId(competitionId).orElseThrow(() -> new IllegalArgumentException());
        List<CompetitionEvent> competitionEventDatum = competitionEventRepository.findCompetitionEventByCompetition(competitionData);

        List<CmptEventResponseDto> competitionEventResponseDatum = competitionEventDatum.stream()
                .map(CompetitionEvent::toDto)
                .collect(Collectors.toList());

        return competitionEventResponseDatum;

    }

}
