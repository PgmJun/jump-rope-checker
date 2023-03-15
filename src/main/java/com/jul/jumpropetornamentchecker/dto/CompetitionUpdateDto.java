package com.jul.jumpropetornamentchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompetitionUpdateDto(Long competitionId, String competitionName, String competitionHost,
                                  String hostEmail, String hostTel, LocalDateTime competitionStartDate, LocalDateTime competitionEndDate) {}
