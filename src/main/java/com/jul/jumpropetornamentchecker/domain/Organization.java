package com.jul.jumpropetornamentchecker.domain;

import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationId")
    private Long orgId;

    @Column(name = "organizationName")
    @NotNull
    private String orgName;

    @Column(name = "organizationEmail")
    @NotNull
    @Email
    private String orgEmail;

    @Column(name = "organizationTel")
    @NotNull
    private String orgTel;

    @Column(name = "organizationLeaderName")
    @NotNull
    private String orgLeaderName;

    @Column(name = "leaderTel")
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
}
