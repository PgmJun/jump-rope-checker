package com.jul.jumpropetornamentchecker.dto;

import com.jul.jumpropetornamentchecker.domain.Competition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CompetitionDto {

    private Long competitionId;

    private String competitionName;

    private String competitionHost;

    private String hostEmail;

    private String hostTel;

    private LocalDateTime competitionStartDate;

    private LocalDateTime competitionEndDate;

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
