package com.jul.jumpropetornamentchecker.dto;

import com.jul.jumpropetornamentchecker.domain.Competition;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompetitionRequestDto(String competitionName, String competitionHost, String hostEmail,
                                    String hostTel, LocalDateTime competitionStartDate, LocalDateTime competitionEndDate) {

    public Competition to() {
        return Competition.builder()
                .competitionName(competitionName())
                .competitionHost(competitionHost())
                .hostEmail(hostEmail())
                .hostTel(hostTel())
                .competitionStartDate(competitionStartDate())
                .competitionEndDate(competitionEndDate())
                .build();
    }
}
