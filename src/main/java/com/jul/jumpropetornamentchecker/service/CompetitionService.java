package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionUpdateDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
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
    private final CompetitionEventService cmptEventService;

    public Boolean saveCompetition(CompetitionRequestDto competitionDto) {
        boolean saveResult = true;
        try {
            Competition savedCompetition = competitionRepository.saveAndFlush(competitionDto.to());
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

    public Optional<CompetitionResponseDto> findCompetitionInfoById(Long competitionId) {
        return competitionRepository.findByCompetitionId(competitionId)
                .stream()
                .map(Competition::toDto)
                .findAny();
    }

    public Boolean removeCompetitionData(List<Long> competitionIds) {
        try {
            competitionIds.forEach(id -> competitionRepository.deleteById(id));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
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
