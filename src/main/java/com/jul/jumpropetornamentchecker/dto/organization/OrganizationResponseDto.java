package com.jul.jumpropetornamentchecker.dto.organization;

import lombok.Builder;

@Builder
public record OrganizationResponseDto(Long orgId, String orgName, String orgEmail, String orgTel, String orgLeaderName,
                                      String leaderTel) {
}
