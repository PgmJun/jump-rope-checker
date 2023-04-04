package com.jul.jumpropetornamentchecker.service;


import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.DepartmentRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
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
    private final DepartmentRepository departmentRepository;
    private final CompetitionEventRepository competitionEventRepository;


    public void saveEventAttendData(CompetitionAttend competitionAttend, CompetitionAttendRequestDto cmptAttendRequestDto) {

        Department department = departmentRepository.findById(cmptAttendRequestDto.getDepartId()).orElseThrow();

        for (Long cmptEventId : cmptAttendRequestDto.getCmptEventIds()) {
            EventAttend eventAttendData = EventAttend.builder()
                    .competitionAttend(competitionAttend)
                    .department(department)
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
}
