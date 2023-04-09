package com.jul.jumpropetornamentchecker.dto.attend.eventAttend;

import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class EventAttendPlayerResponseDto {
    private String cmptAttendId;
    private String departmentName;
    private String organizationName;
    private String competitionName;
    private String playerName;
    private String playerGender;
    private Long eventAttendId;
    private String eventName;
    private int grade;
    private int count;

    public static EventAttendPlayerResponseDto from(CompetitionAttendResponseDto cmptAttendResponseDto, EventAttendResponseDto eventAttendResponseDto) {
        return EventAttendPlayerResponseDto.builder()
                .grade(eventAttendResponseDto.getGrade())
                .count(eventAttendResponseDto.getCnt())
                .eventName(eventAttendResponseDto.getCmptEventName())
                .eventAttendId(eventAttendResponseDto.getEventAttendId())
                .cmptAttendId(cmptAttendResponseDto.getCmptAttendId())
                .departmentName(cmptAttendResponseDto.getDepartmentName())
                .organizationName(cmptAttendResponseDto.getOrganizationName())
                .competitionName(cmptAttendResponseDto.getCompetitionName())
                .playerName(cmptAttendResponseDto.getPlayerName())
                .playerGender(cmptAttendResponseDto.getPlayerGender())
                .build();
    }
}
