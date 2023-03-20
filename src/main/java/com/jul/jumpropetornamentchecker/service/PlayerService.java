package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.Player;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
