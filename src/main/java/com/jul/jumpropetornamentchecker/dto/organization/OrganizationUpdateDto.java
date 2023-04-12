package com.jul.jumpropetornamentchecker.dto.organization;

import lombok.Builder;

@Builder
public record OrganizationUpdateDto(Long orgId, String orgName, String orgEmail, String orgTel, String orgLeaderName,
                                    String leaderTel) {
}
