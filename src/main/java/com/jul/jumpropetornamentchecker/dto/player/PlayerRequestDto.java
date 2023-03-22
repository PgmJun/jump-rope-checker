package com.jul.jumpropetornamentchecker.dto.player;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.player.Player;
import lombok.Builder;

@Builder
public record PlayerRequestDto(Long organizationId, String playerName, String playerGender, int playerAge,
                               String playerTel) {

    public Player from(Organization organization) {
        return Player.builder()
                .organization(organization)
                .playerRequestDto(this)
                .build();
    }

}
