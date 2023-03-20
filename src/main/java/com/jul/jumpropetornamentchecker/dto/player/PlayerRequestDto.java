package com.jul.jumpropetornamentchecker.dto.player;

import lombok.Builder;

@Builder
public record PlayerRequestDto(Long organizationId, String playerName, String playerSex, int playerAge, String playerTel) {
}
