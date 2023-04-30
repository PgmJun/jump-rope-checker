package com.jul.jumpropetornamentchecker.dto.attend;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class CompetitionAttendUpdateDto {

    private Long departId;
    private String playerName;
    private String playerGender;
    private String playerBirth;
    private String playerTel;
    private String playerAffiliation;
}
