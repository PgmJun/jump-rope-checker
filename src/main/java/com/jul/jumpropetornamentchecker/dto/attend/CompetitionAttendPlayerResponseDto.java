package com.jul.jumpropetornamentchecker.dto.attend;

import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CompetitionAttendPlayerResponseDto {
    private String cmptAttendId;
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
    private int score;

    public static CompetitionAttendPlayerResponseDto from(CompetitionAttendResponseDto cmptAttendResponseDto, EventAttendResponseDto eventAttendResponseDto) {
        return CompetitionAttendPlayerResponseDto.builder()
                .score(eventAttendResponseDto.getScore())
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
