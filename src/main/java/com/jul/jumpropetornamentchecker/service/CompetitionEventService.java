package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.event.EventData;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CmptEventResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionEventService {

    private final CompetitionEventRepository competitionEventRepository;
    private final CompetitionRepository competitionRepository;


    public void saveDefaultCompetitionEventData(Competition competition) {
        try {
            EventData.getAllEventData().forEach(event -> {
                competitionEventRepository.save(CompetitionEvent.builder()
                        .competition(competition)
                        .event(event)
                        .isProceed(false)
                        .partPoint(0)
                        .fstPrizeStandard(0)
                        .sndPrizeStandard(0)
                        .trdPrizeStandard(0)
                        .build());
            });

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public List<CmptEventResponseDto> findCompetitionEventDataByCompetitionId(Long competitionId) {
        Competition competitionData = competitionRepository.findByCompetitionId(competitionId).orElseThrow(() -> new IllegalArgumentException());
        List<CompetitionEvent> competitionEventDatum = competitionEventRepository.findCompetitionEventByCompetition(competitionData);

        List<CmptEventResponseDto> competitionEventResponseDatum = competitionEventDatum.stream()
                .map(CompetitionEvent::toDto)
                .collect(Collectors.toList());

        return competitionEventResponseDatum;
    }

}
