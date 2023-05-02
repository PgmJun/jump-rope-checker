package com.jul.jumpropetornamentchecker.dto.prize;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PrizeResponseDto {
    private String cmptName;
    private String grade;
    private String playerAffiliation;
    private String playerName;
    private boolean isPrinted;
}
