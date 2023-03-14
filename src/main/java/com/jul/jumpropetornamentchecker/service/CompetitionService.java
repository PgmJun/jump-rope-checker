package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.dto.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionUpdateDto;
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
public class CompetitionService {
    private final CompetitionRepository competitionRepository;

    public Boolean saveCompetition(CompetitionRequestDto competitionDto) {
        try {
            competitionRepository.save(competitionDto.to());
            return true;
        } catch (Exception e) {
            return false;
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
            return false;
        }
    }

    public boolean updateCompetitionData(CompetitionUpdateDto competitionUpdateData) {
        try {
            Competition competitionData = competitionRepository.findByCompetitionId(competitionUpdateData.competitionId()).orElseThrow();
            competitionData.changeData(competitionUpdateData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
