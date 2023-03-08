package com.jul.jumpropetornamentchecker.dto;

import com.jul.jumpropetornamentchecker.domain.Competition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CompetitionDto {

    private Long competition_id;

    private String competition_name;

    private String competition_host;

    private String host_email;

    private String host_tel;

    private LocalDateTime competition_start_date;

    private LocalDateTime competition_end_date;

    public Competition to() {
        return Competition.builder()
                .competition_name(this.competition_name)
                .competition_host(this.competition_host)
                .host_email(this.host_email)
                .host_tel(this.host_tel)
                .competition_start_date(this.competition_start_date)
                .competition_end_date(this.competition_end_date)
                .build();
    }
}
