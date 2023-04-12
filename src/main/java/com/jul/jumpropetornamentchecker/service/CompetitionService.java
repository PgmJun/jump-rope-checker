package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionUpdateDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionAttendRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionEventRepository cmptEventRepository;
    private final EventAttendRepository eventAttendRepository;
    private final CompetitionAttendRepository cmptAttendRepository;
    private final CompetitionEventService cmptEventService;

    public Boolean saveCompetition(CompetitionRequestDto competitionDto) {
        boolean saveResult = true;
        try {
            Competition savedCompetition = competitionRepository.save(competitionDto.to());
            cmptEventService.saveDefaultCompetitionEventData(savedCompetition);
        } catch (Exception e) {
            log.error(e.getMessage());
            saveResult = false;
        } finally {
            return saveResult;
        }
    }

    public List<CompetitionResponseDto> findAllCompetitionInfo() {
        List<Competition> allCompetitionInfo = competitionRepository.findAll();

        return allCompetitionInfo.stream()
                .map(Competition::toDto)
                .collect(Collectors.toList());
    }

    public List<CompetitionResponseDto> findCompetitionInfoByName(String competitionName) {
        List<Competition> competitionInfos = competitionRepository.findByCompetitionName(competitionName);

        return competitionInfos.stream()
                .map(Competition::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Competition> findCompetitionInfoById(Long competitionId) {
        return competitionRepository.findByCompetitionId(competitionId);
    }

    @Transactional
    public Boolean removeCompetitionData(Long cmptId) {
        boolean removeResult = true;

        try {
            Competition competition = competitionRepository.findById(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

            //대회참가 CompetitionAttend 삭제
            //종목참가 EventAttend 삭제
            List<CompetitionAttend> cmptAttends = cmptAttendRepository.findByCompetition(competition);
            cmptAttends.forEach(c -> {
                eventAttendRepository.deleteByCompetitionAttend(c);
                cmptAttendRepository.delete(c);
            });

            //대회종목 CompetitionEvent 삭제
            cmptEventRepository.deleteByCompetition(competition);

            //대회 삭제
            competitionRepository.deleteById(cmptId);

        } catch (Exception e) {
            log.error(e.getMessage());
            removeResult = false;

        } finally {
            return removeResult;
        }
    }

    @Transactional
    public boolean updateCompetitionData(CompetitionUpdateDto competitionUpdateData) {
        boolean updateResult = true;
        try {
            Competition competitionData = competitionRepository.findByCompetitionId(competitionUpdateData.competitionId()).orElseThrow();
            competitionData.changeData(competitionUpdateData);

        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;

        } finally {
            return updateResult;
        }
    }
}
