package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.domain.event.EventData;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventUpdateDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
                        .isProceed(Boolean.FALSE)
                        .partPoint(0)
                        .fstPrizeStandard(0)
                        .sndPrizeStandard(0)
                        .trdPrizeStandard(0)
                        .build());
            });

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<CompetitionEventResponseDto> findCompetitionEventDataByCompetitionId(Long competitionId) {
        Competition competitionData = competitionRepository.findByCompetitionId(competitionId).orElseThrow(IllegalArgumentException::new);
        List<CompetitionEvent> competitionEventDatum = competitionEventRepository.findCompetitionEventByCompetition(competitionData);

        List<CompetitionEventResponseDto> competitionEventResponseDatum = competitionEventDatum.stream()
                .map(CompetitionEvent::toDto)
                .collect(Collectors.toList());

        return competitionEventResponseDatum;
    }

    @Transactional
    public boolean updateCompetitionEventData(List<CompetitionEventUpdateDto> competitionEventUpdateDtos) {
        boolean updateResult = true;
        try {
            for (CompetitionEventUpdateDto updateDto : competitionEventUpdateDtos) {
                CompetitionEvent competitionEvent = competitionEventRepository.findById(updateDto.cmptEventId()).orElseThrow(() -> new IllegalArgumentException("입력받은 cmptEventId " + updateDto.cmptEventId() + "가 존재하지 않거나 잘못되었습니다."));
                competitionEvent.changeData(updateDto);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;
        } finally {
            return updateResult;
        }
    }

}
