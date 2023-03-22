package com.jul.jumpropetornamentchecker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.player.Gender;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerResponseDto;
import com.jul.jumpropetornamentchecker.repository.OrganizationRepository;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import com.jul.jumpropetornamentchecker.service.PlayerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private OrganizationService orgService;
    @Autowired
    private OrganizationRepository orgRepository;

    private String orgName = "testOrg";
    private String orgEmail = "testOrg@test.com";
    private String orgTel = "02-123-1234";
    private String orgLeaderName = "testLeader";
    private String leaderTel = "010-1234-1234";

    @Test
    @DisplayName("선수 등록 기능 테스트")
    void testInsertPlayerData() throws Exception {
        OrganizationRequestDto testOrg = createTestOrgDto();
        orgService.saveOrganization(testOrg);

        OrganizationResponseDto orgResponseDto = orgService.findOrganizationByName(orgName).get(0);
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(orgResponseDto.orgId(), "playerName", "male", 20, "010-1234-1234");
        String playerDataJsonString = objectMapper.writeValueAsString(playerRequestDto);

        mockMvc.perform(post("/player/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDataJsonString))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("선수 이름 조회 기능 테스트")
    void testFindPlayerDataByName() throws Exception {
        OrganizationRequestDto testOrg = createTestOrgDto();
        orgService.saveOrganization(testOrg);

        Organization organization = orgRepository.findByOrgName(testOrg.orgName()).get(0);
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(organization.getOrgId(), "playerName", "male", 20, "010-1234-1234");

        playerService.savePlayer(playerRequestDto);

        mockMvc.perform(get("/player/find")
                        .param("name", playerRequestDto.playerName()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선수 이름 조회 실패 기능 테스트")
    void testNotFoundPlayerDataByName() throws Exception {
        mockMvc.perform(get("/player/find")
                        .param("name", "testName"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DisplayName("선수 ID 조회 기능 테스트")
    void testFindPlayerDataById() throws Exception {
        OrganizationRequestDto testOrg = createTestOrgDto();
        orgService.saveOrganization(testOrg);

        Organization organization = orgRepository.findByOrgName(testOrg.orgName()).get(0);
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(organization.getOrgId(), "playerName", "male", 20, "010-1234-1234");

        playerService.savePlayer(playerRequestDto);
        PlayerResponseDto player = playerService.findPlayerByName(playerRequestDto.playerName()).get(0);

        mockMvc.perform(get("/player/find/" + player.playerId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("단체 소속 선수 조회 기능 테스트")
    void testFindPlayerDataOrganizationId() throws Exception {
        mockMvc.perform(get("/player/find/org/0"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("단체 소속 선수 조회 실패 기능 테스트")
    void testNotFoundPlayerDataOrganizationId() throws Exception {
        OrganizationRequestDto testOrg = createTestOrgDto();
        orgService.saveOrganization(testOrg);

        Organization organization = orgRepository.findByOrgName(testOrg.orgName()).get(0);
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(organization.getOrgId(), "playerName", "male", 20, "010-1234-1234");

        playerService.savePlayer(playerRequestDto);

        mockMvc.perform(get("/player/find/org/"+organization.getOrgId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선수 ID 조회 실패 기능 테스트")
    void testNotFoundPlayerDataById() throws Exception {
        mockMvc.perform(get("/player/find/0"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("선수 정보 삭제 기능 테스트")
    void testRemovePlayerDataById() throws Exception {
        OrganizationRequestDto testOrg = createTestOrgDto();
        orgService.saveOrganization(testOrg);

        Organization organization = orgRepository.findByOrgName(testOrg.orgName()).get(0);
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(organization.getOrgId(), "playerName", "male", 20, "010-1234-1234");

        playerService.savePlayer(playerRequestDto);
        PlayerResponseDto player = playerService.findPlayerByName(playerRequestDto.playerName()).get(0);
        String playerIdsJsonString = objectMapper.writeValueAsString(List.of(player.playerId()));

        mockMvc.perform(delete("/player/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerIdsJsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }


    private OrganizationRequestDto createTestOrgDto() {
        return OrganizationRequestDto.builder()
                .orgName(orgName)
                .orgEmail(orgEmail)
                .orgTel(orgTel)
                .orgLeaderName(orgLeaderName)
                .leaderTel(leaderTel)
                .build();
    }
}