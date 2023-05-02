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

import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompetitionAttend {

    @Id
    private String cmptAttendId;

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
    private String playerTel;

    @Column
    private String playerAffiliation; //선수 소속 (상장 출력용 소속)


    public static CompetitionAttend from(Competition competition, Department department, Organization organization, CompetitionAttendRequestDto cmptAttendRequestDto) {
        return CompetitionAttend.builder()
                .cmptAttendId(competition.getCompetitionId() + "-" + department.getDepartId() + "-" + competition.getPlayerNumber())
                .competition(competition)
                .department(department)
                .organization(organization)
                .playerName(cmptAttendRequestDto.getPlayerName())
                .playerGender(Gender.findByType(cmptAttendRequestDto.getPlayerGender()))
                .playerBirth(cmptAttendRequestDto.getPlayerBirth())
                .playerTel(cmptAttendRequestDto.getPlayerTel())
                .playerAffiliation(cmptAttendRequestDto.getPlayerAffiliation())
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
                .playerAffiliation(playerAffiliation)
                .build();
    }

    public CompetitionAttendResponseDto toDto(List<Long> cmptEventIds) {
        return CompetitionAttendResponseDto.builder()
                .cmptAttendId(cmptAttendId)
                .departmentName(department.getDepartName())
                .departId(department.getDepartId())
                .competitionName(competition.getCompetitionName())
                .organizationName(organization.getOrgName())
                .playerName(playerName)
                .playerGender(playerGender.getType())
                .playerBirth(playerBirth)
                .playerTel(playerTel)
                .playerAffiliation(playerAffiliation)
                .cmptEventIds(cmptEventIds)
                .build();
    }
}
