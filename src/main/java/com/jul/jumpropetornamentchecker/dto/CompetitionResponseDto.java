package com.jul.jumpropetornamentchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record CompetitionResponseDto(Long competitionId, String competitionName, String recordingSheetName,
                                     String competitionHost,
                                     String hostEmail, String hostTel, LocalDateTime competitionStartDate,
                                     LocalDateTime competitionEndDate) {

}
