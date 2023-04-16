package com.jul.jumpropetornamentchecker.domain;

import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationId")
    private Long orgId;

    @Column(name = "organizationName")
    @NotNull
    private String orgName;

    @Column(name = "organizationEmail")
    private String orgEmail;

    @Column(name = "organizationTel")
    private String orgTel;

    @Column(name = "organizationLeaderName")
    @NotNull
    private String orgLeaderName;

    @Column
    private String leaderTel;

    @Builder
    public Organization(String orgName, String orgEmail, String orgTel, String orgLeaderName, String leaderTel) {
        this.orgName = orgName;
        this.orgEmail = orgEmail;
        this.orgTel = orgTel;
        this.orgLeaderName = orgLeaderName;
        this.leaderTel = leaderTel;
    }

    public OrganizationResponseDto toDto() {
        return OrganizationResponseDto.builder()
                .orgId(orgId)
                .orgName(orgName)
                .orgEmail(orgEmail)
                .orgTel(orgTel)
                .orgLeaderName(orgLeaderName)
                .leaderTel(leaderTel)
                .build();
    }

    public void changeData(OrganizationUpdateDto updateData) {
        orgName = updateData.orgName();
        orgEmail = updateData.orgEmail();
        orgTel = updateData.orgTel();
        orgLeaderName = updateData.orgLeaderName();
        leaderTel = updateData.leaderTel();
    }
}
