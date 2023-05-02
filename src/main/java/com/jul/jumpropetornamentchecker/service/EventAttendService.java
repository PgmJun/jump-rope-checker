package com.jul.jumpropetornamentchecker.service;


import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendUpdateDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionAttendRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionEventRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventAttendService {

    private final EventAttendRepository eventAttendRepository;
    private final CompetitionEventRepository competitionEventRepository;
    private final CompetitionAttendRepository cmptAttendRepository;


    public void saveEventAttendData(Competition competition, CompetitionAttend competitionAttend, CompetitionAttendRequestDto cmptAttendRequestDto) {

        for (Long cmptEventId : cmptAttendRequestDto.getCmptEventIds()) {
            EventAttend eventAttendData = EventAttend.builder()
                    .competitionAttend(competitionAttend)
                    .competitionEvent(competitionEventRepository.findById(cmptEventId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회종목ID입니다.")))
                    .competition(competition)
                    .score(0)
                    .grade(0)
                    .isPrinted(false)
                    .build();

            eventAttendRepository.save(eventAttendData);
        }
    }

    public List<EventAttendResponseDto> findEventAttendByCmptAttend(CompetitionAttend cmptAttend) {
        List<EventAttend> cmptAttendDatum = eventAttendRepository.findByCompetitionAttend(cmptAttend);
        return cmptAttendDatum.stream().map(EventAttend::toDto).collect(Collectors.toList());
    }

    public Boolean updatePlayerEventScoreByCompetitionAttendId(String cmptAttendId, EventAttendUpdateDto updateData) {
        boolean updateResult = true;

        try {
            CompetitionAttend competitionAttend = cmptAttendRepository.findById(cmptAttendId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회참가ID입니다."));
            CompetitionEvent competitionEvent = competitionEventRepository.findById(updateData.getCmptEventId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회종목ID입니다."));
            int grade = 0;
            Department department = competitionAttend.getDepartment();
            Map<Long, List<Integer>> standardValue = competitionEvent.toDto().getStandardValue();

            List<Integer> CmptEventPrizeStandardListOfDepartment = standardValue.get(department.getDepartId());

            // 해당 부서의 해당 대회종목의 수상 기준을 금,은,동 순으로 조회하여 수상 여부 입력
            // 금: 3 , 은: 2 , 동: 1 , 미수상: 0(default)
            for (int i = CmptEventPrizeStandardListOfDepartment.size() - 1; i >= 0; i--) {
                if (updateData.getScore() >= CmptEventPrizeStandardListOfDepartment.get(i)) {
                    grade = i + 1;
                    break;
                }
            }

            eventAttendRepository.updatePlayerEventScore(competitionAttend, competitionEvent, grade, updateData.getScore());

        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;
        } finally {
            return updateResult;
        }
    }
}
