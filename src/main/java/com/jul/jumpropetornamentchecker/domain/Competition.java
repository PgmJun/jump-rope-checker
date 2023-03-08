package com.jul.jumpropetornamentchecker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "competition")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long competition_id;

    @Column
    @NotNull
    private String competition_name;

    @Column
    @NotNull
    private String competition_host;

    @Column
    @Email
    private String host_email;

    @Column
    @NotNull
    private String host_tel;

    @Column
    @NotNull
    private LocalDateTime competition_start_date;

    @Column
    @NotNull
    private LocalDateTime competition_end_date;


    @Builder
    public Competition(String competition_name, String competition_host, String host_email, String host_tel, LocalDateTime competition_start_date, LocalDateTime competition_end_date) {
        this.competition_name = competition_name;
        this.competition_host = competition_host;
        this.host_email = host_email;
        this.host_tel = host_tel;
        this.competition_start_date = competition_start_date;
        this.competition_end_date = competition_end_date;
    }
}
