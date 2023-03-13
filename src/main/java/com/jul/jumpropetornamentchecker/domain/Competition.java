package com.jul.jumpropetornamentchecker.domain;

import com.jul.jumpropetornamentchecker.dto.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "competition")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long competitionId;

    @Column
    @NotNull
    private String competitionName;

    @Column
    @NotNull
    private String competitionHost;

    @Column
    @Email
    private String hostEmail;

    @Column
    @NotNull
    private String hostTel;

    @Column
    @NotNull
    private LocalDate competitionStartDate;

    @Column
    @NotNull
    private LocalDate competitionEndDate;

    @Builder
    public Competition(String competitionName, String competitionHost, String hostEmail, String hostTel, LocalDate competitionStartDate, LocalDate competitionEndDate) {
        this.competitionName = competitionName;
        this.competitionHost = competitionHost;
        this.hostEmail = hostEmail;
        this.hostTel = hostTel;
        this.competitionStartDate = competitionStartDate;
        this.competitionEndDate = competitionEndDate;
    }

    public CompetitionResponseDto toDto() {
        return CompetitionResponseDto.builder()
                .competitionId(competitionId)
                .competitionName(competitionName)
                .competitionHost(competitionHost)
                .hostEmail(hostEmail)
                .hostTel(hostTel)
                .competitionStartDate(competitionStartDate)
                .competitionEndDate(competitionEndDate)
                .build();
    }

    public void changeData(CompetitionUpdateDto updateData) {
        this.competitionName = updateData.competitionName();
        this.competitionHost = updateData.competitionHost();
        this.hostEmail = updateData.hostEmail();
        this.hostTel = updateData.hostTel();
        this.competitionStartDate = updateData.competitionStartDate();
        this.competitionEndDate = updateData.competitionEndDate();
    }
}
