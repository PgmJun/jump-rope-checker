package com.jul.jumpropetornamentchecker.dto.organization;

import com.jul.jumpropetornamentchecker.domain.Organization;
import lombok.Builder;

@Builder
public record OrganizationRequestDto(String orgName, String orgEmail, String orgTel, String orgLeaderName,
                                     String leaderTel) {

    public Organization to() {
        return Organization.builder()
                .orgName(orgName())
                .orgEmail(orgEmail())
                .orgTel(orgTel())
                .orgLeaderName(orgLeaderName())
                .leaderTel(leaderTel())
                .build();
    }

}
