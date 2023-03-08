package com.jul.jumpropetornamentchecker.dto;

import com.jul.jumpropetornamentchecker.domain.Competition;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetitionRequestDto {

    private String competitionName;

    private String competitionHost;

    private String hostEmail;

    private String hostTel;

    private LocalDate competitionStartDate;

    private LocalDate competitionEndDate;

    public Competition to() {
        return Competition.builder()
                .competitionName(this.competitionName)
                .competitionHost(this.competitionHost)
                .hostEmail(this.hostEmail)
                .hostTel(this.hostTel)
                .competitionStartDate(this.competitionStartDate)
                .competitionEndDate(this.competitionEndDate)
                .build();
    }
}
