package com.jul.jumpropetornamentchecker.domain.attend;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
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

    @ManyToOne
    @JoinColumn(name = "competitionId")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "orgId")
    private Organization organization;

    @OneToOne
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

    public CompetitionAttendResponseDto toDto() {
        return CompetitionAttendResponseDto.builder()
                .cmptAttendId(cmptAttendId)
                .departmentName(department.getDepartName())
                .competitionName(competition.getCompetitionName())
                .organizationName(organization.getOrgName())
                .playerName(playerName)
                .playerGender(playerGender.getType())
                .playerBirth(playerBirth)
                .playerTel(playerTel)
                .build();
    }
}
