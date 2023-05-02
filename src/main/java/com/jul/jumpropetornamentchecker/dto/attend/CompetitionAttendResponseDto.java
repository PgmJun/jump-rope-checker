package com.jul.jumpropetornamentchecker.dto.attend;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CompetitionAttendResponseDto {
    private String cmptAttendId;
    private String departmentName;
    private Long departId;
    private String organizationName;
    private String competitionName;
    private String playerBirth;
    private String playerName;
    private String playerGender;
    private String playerTel;
    private String playerAffiliation;
    private List<Long> cmptEventIds;
}
