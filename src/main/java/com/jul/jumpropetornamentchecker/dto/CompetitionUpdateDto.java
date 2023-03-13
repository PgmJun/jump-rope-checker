package com.jul.jumpropetornamentchecker.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CompetitionUpdateDto(Long competitionId, String competitionName, String competitionHost,
                                  String hostEmail, String hostTel, LocalDate competitionStartDate, LocalDate competitionEndDate) {}
