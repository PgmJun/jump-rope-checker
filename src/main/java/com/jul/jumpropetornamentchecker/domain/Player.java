package com.jul.jumpropetornamentchecker.domain;

import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
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

    @Column(name = "playerSex")
    @NotNull
    private String playerSex;

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
        this.playerSex = playerRequestDto.playerSex();
        this.playerAge = playerRequestDto.playerAge();
        this.playerTel = playerRequestDto.playerTel();
    }
}
