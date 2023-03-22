package com.jul.jumpropetornamentchecker.domain.player;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @Column(name = "playerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @ManyToOne
    @JoinColumn(name = "orgId")
    private Organization organization;

    @Column(name = "playerName")
    @NotNull
    private String playerName;

    @Column(name = "playerGender")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender playerGender;

    @Column(name = "playerAge")
    @NotNull
    private int playerAge;

    @Column(name = "playerTel")
    @NotNull
    private String playerTel;

    @Builder
    public Player(Organization organization, PlayerRequestDto playerRequestDto) {
        this.organization = organization;
        this.playerName = playerRequestDto.playerName();
        this.playerGender = Gender.findByType(playerRequestDto.playerGender().toUpperCase());
        this.playerAge = playerRequestDto.playerAge();
        this.playerTel = playerRequestDto.playerTel();
    }

    public PlayerResponseDto toDto() {
        return PlayerResponseDto.builder()
                .organizationName(organization.getOrgName())
                .playerId(playerId)
                .playerAge(playerAge)
                .playerName(playerName)
                .playerGender(playerGender.name())
                .playerTel(playerTel)
                .build();
    }
}
