package com.jul.jumpropetornamentchecker.dto;

import lombok.*;

import java.time.LocalDate;


@Builder
public record CompetitionResponseDto(Long competitionId,String competitionName,String competitionHost,
                                     String hostEmail, String hostTel, LocalDate competitionStartDate, LocalDate competitionEndDate){

}
