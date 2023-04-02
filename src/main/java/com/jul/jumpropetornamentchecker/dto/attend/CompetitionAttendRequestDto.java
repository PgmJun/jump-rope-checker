package com.jul.jumpropetornamentchecker.dto.attend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CompetitionAttendRequestDto {

    private Long cmptId;
    private Long orgId;
    private List<Long> cmptEventIds;
    private Long departId;
    private String playerName;
    private String playerGender;
    private String playerBirth;
    private String playerTel;
}
