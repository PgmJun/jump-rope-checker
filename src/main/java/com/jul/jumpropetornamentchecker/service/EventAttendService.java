package com.jul.jumpropetornamentchecker.service;


import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendUpdateDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionAttendRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.DepartmentRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventAttendService {

    private final EventAttendRepository eventAttendRepository;
    private final CompetitionEventRepository competitionEventRepository;
    private final CompetitionAttendRepository cmptAttendRepository;



    public void saveEventAttendData(CompetitionAttend competitionAttend, CompetitionAttendRequestDto cmptAttendRequestDto) {

        for (Long cmptEventId : cmptAttendRequestDto.getCmptEventIds()) {
            EventAttend eventAttendData = EventAttend.builder()
                    .competitionAttend(competitionAttend)
                    .competitionEvent(competitionEventRepository.findById(cmptEventId).orElseThrow())
                    .grade(0)
                    .cnt(0)
                    .build();

            eventAttendRepository.save(eventAttendData);
        }
    }

    public List<EventAttendResponseDto> findEventAttendByCmptAttend(CompetitionAttend cmptAttend) {
        List<EventAttend> cmptAttendDatum = eventAttendRepository.findByCompetitionAttend(cmptAttend);
        return cmptAttendDatum.stream().map(EventAttend::toDto).collect(Collectors.toList());
    }

    public Boolean updatePlayerEventScoreByCompetitionAttendId(Long cmptAttendId, EventAttendUpdateDto updateData) {
        boolean updateResult = true;

        try {
            CompetitionAttend competitionAttend = cmptAttendRepository.findById(cmptAttendId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회참가ID입니다."));
            eventAttendRepository.updatePlayerEventScore(competitionAttend, updateData.getCmptEventId(), updateData.getGrade(), updateData.getCount());
        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;
        } finally {
            return updateResult;
        }
    }
}
