package com.jul.jumpropetornamentchecker.dto.attend;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompetitionAttendResponseDto {
    private String organizationName;
    private String competitionName;
    private Long playerId;
    private int playerAge;
    private String playerName;
    private String playerGender;
    private String playerTel;
}
