package com.jul.jumpropetornamentchecker.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import com.jul.jumpropetornamentchecker.service.PlayerService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PlayerControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PlayerService playerService;
    @Autowired private OrganizationService organizationService;

    private String orgName = "testOrg";
    private String orgEmail = "testOrg@test.com";
    private String orgTel = "02-123-1234";
    private String orgLeaderName = "testLeader";
    private String leaderTel = "010-1234-1234";

    @Test
    @DisplayName("선수 등록 기능 테스트")
    void testInsertPlayerData() throws Exception {
        OrganizationRequestDto testDto = createTestOrgDto();
        organizationService.saveOrganization(testDto);

        OrganizationResponseDto orgResponseDto = organizationService.findOrganizationByName(orgName).get(0);
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(orgResponseDto.orgId(), "playerName", "M", 20, "010-1234-1234");
        String playerDataJsonString = objectMapper.writeValueAsString(playerRequestDto);
        
        mockMvc.perform(post("/player/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDataJsonString))
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