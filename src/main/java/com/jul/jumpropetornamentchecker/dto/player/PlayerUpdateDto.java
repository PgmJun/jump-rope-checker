package com.jul.jumpropetornamentchecker.dto.player;

import lombok.Builder;

@Builder
public record PlayerUpdateDto(Long playerId, String playerName, String playerGender,
                              int playerAge, String playerTel) {
}