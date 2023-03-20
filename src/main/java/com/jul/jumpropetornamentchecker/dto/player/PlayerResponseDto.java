package com.jul.jumpropetornamentchecker.dto.player;

import lombok.Builder;

@Builder
public record PlayerResponseDto(Long playerId, String playerName, String playerSex, int playerAge, String playerTel) {
}
