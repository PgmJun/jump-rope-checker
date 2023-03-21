package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.Player;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerResponseDto;
import com.jul.jumpropetornamentchecker.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final OrganizationService organizationService;

    public Boolean savePlayer(PlayerRequestDto playerRequestDto) {
        boolean saveResult = true;

        try {
            Organization organization = organizationService.findOrganizationById(playerRequestDto.organizationId()).orElseThrow();
            Player player = new Player(organization, playerRequestDto);

            playerRepository.save(player);

        } catch (Exception e) {
            log.error(e.getMessage());
            saveResult = false;

        } finally {
            return saveResult;
        }
    }

    public List<PlayerResponseDto> findAllPlayer() {
        return playerRepository.findAll()
                .stream()
                .map(Player::toDto)
                .collect(Collectors.toList());
    }

    public List<PlayerResponseDto> findPlayerByName(String name) {
        List<PlayerResponseDto> playerDatum = new ArrayList<>();

        try {
            playerDatum = playerRepository.findByPlayerName(name).stream()
                    .map(Player::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error(e.getMessage());

        } finally {
            return playerDatum;
        }
    }

    public Optional<Player> findPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public boolean removePlayerData(List<Long> playerIds) {
        boolean removeResult = true;

        try {
            playerIds.forEach(id -> playerRepository.deleteByPlayerId(id));

        } catch (Exception e) {
            log.error(e.getMessage());
            removeResult = false;

        } finally {
            return removeResult;
        }
    }


    public List<PlayerResponseDto> findPlayerDataByOrganizationId(Long organizationId) {
        List<PlayerResponseDto> playerDatum = new ArrayList<>();
        try {
            Organization organization = organizationService.findOrganizationById(organizationId).orElseThrow();
            playerDatum = playerRepository.findByOrganization(organization)
                    .stream()
                    .map(Player::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error(e.getMessage());

        } finally {
            return playerDatum;
        }
    }
}
