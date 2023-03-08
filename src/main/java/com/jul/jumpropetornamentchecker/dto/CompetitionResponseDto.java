package com.jul.jumpropetornamentchecker.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetitionResponseDto {

    private Long competitionId;

    private String competitionName;

    private String competitionHost;

    private String hostEmail;

    private String hostTel;

    private LocalDate competitionStartDate;

    private LocalDate competitionEndDate;

}
