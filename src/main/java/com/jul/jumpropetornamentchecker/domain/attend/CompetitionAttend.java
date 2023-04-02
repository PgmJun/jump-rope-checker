package com.jul.jumpropetornamentchecker.domain.attend;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompetitionAttend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmptAttendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competitionId")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgId")
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departId")
    private Department department;

    @Column
    @NotNull
    private String playerName;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender playerGender;

    @Column
    @NotNull
    private String playerBirth;

    @Column
    @NotNull
    private String playerTel;


    public static CompetitionAttend from(Competition competition, Department department, Organization organization, CompetitionAttendRequestDto cmptAttendRequestDto) {
        return CompetitionAttend.builder()
                .competition(competition)
                .department(department)
                .organization(organization)
                .playerName(cmptAttendRequestDto.getPlayerName())
                .playerGender(Gender.findByType(cmptAttendRequestDto.getPlayerGender()))
                .playerBirth(cmptAttendRequestDto.getPlayerBirth())
                .playerTel(cmptAttendRequestDto.getPlayerTel())
                .build();
    }
}
