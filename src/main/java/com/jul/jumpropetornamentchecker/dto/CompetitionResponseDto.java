package com.jul.jumpropetornamentchecker.dto;

import lombok.*;

import java.time.LocalDateTime;


@Builder
public record CompetitionResponseDto(Long competitionId,String competitionName,String competitionHost,
                                     String hostEmail, String hostTel, LocalDateTime competitionStartDate, LocalDateTime competitionEndDate){

}
