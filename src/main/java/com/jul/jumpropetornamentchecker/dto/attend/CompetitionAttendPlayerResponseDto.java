package com.jul.jumpropetornamentchecker.dto.attend;

import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CompetitionAttendPlayerResponseDto {
    private Long cmptAttendId;
    private String departmentName;
    private String organizationName;
    private String competitionName;
    private String playerBirth;
    private String playerName;
    private String playerGender;
    private String playerTel;
    private String playerAffiliation;
    private Long eventAttendId;
    private String eventName;
    private int grade;
    private int count;

    public static CompetitionAttendPlayerResponseDto from(CompetitionAttendResponseDto cmptAttendResponseDto, EventAttendResponseDto eventAttendResponseDto) {
        return CompetitionAttendPlayerResponseDto.builder()
                .grade(eventAttendResponseDto.getGrade())
                .count(eventAttendResponseDto.getCnt())
                .eventName(eventAttendResponseDto.getCmptEventName())
                .eventAttendId(eventAttendResponseDto.getEventAttendId())
                .cmptAttendId(cmptAttendResponseDto.getCmptAttendId())
                .departmentName(cmptAttendResponseDto.getDepartmentName())
                .organizationName(cmptAttendResponseDto.getOrganizationName())
                .competitionName(cmptAttendResponseDto.getCompetitionName())
                .playerBirth(cmptAttendResponseDto.getPlayerBirth())
                .playerName(cmptAttendResponseDto.getPlayerName())
                .playerGender(cmptAttendResponseDto.getPlayerGender())
                .playerTel(cmptAttendResponseDto.getPlayerTel())
                .playerAffiliation(cmptAttendResponseDto.getPlayerAffiliation())
                .build();
    }
}
