package com.jul.jumpropetornamentchecker.dto.player;

import lombok.Builder;

@Builder
public record PlayerResponseDto(String organizationName, Long playerId, String playerName, String playerSex, int playerAge, String playerTel) {
}
