package com.jul.jumpropetornamentchecker.dto.competition;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompetitionUpdateDto(Long competitionId, String competitionName, String recordingSheetName, String competitionHost,
                                   String hostEmail, String hostTel, LocalDateTime competitionStartDate,
                                   LocalDateTime competitionEndDate) {
}
