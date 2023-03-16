package com.jul.jumpropetornamentchecker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrganizationService organizationService;


    private String orgName = "testOrg";
    private String orgEmail = "testOrg@test.com";
    private String orgTel = "02-123-1234";
    private String orgLeaderName = "testLeader";
    private String leaderTel = "010-1234-1234";

    @Test
    @DisplayName("단체 추가 기능 테스트")
    void testInsertOrganizationData() throws Exception {
        String orgDataJsonString = objectMapper.writeValueAsString(createTestDto());

        mockMvc.perform(post("/organization/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orgDataJsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("단체 이름 조회 기능 테스트")
    void testFindOrganizationDataByName() {
        OrganizationRequestDto testDto = createTestDto();
        organizationService.saveOrganization(testDto);

        List<OrganizationResponseDto> organizationDatum = organizationService.findOrganizationByName(testDto.orgName());
        organizationDatum.forEach(orgData -> assertThat(orgData.orgName()).contains(testDto.orgName()));

    }

    private OrganizationRequestDto createTestDto() {
        return OrganizationRequestDto.builder()
                .orgName(orgName)
                .orgEmail(orgEmail)
                .orgTel(orgTel)
                .orgLeaderName(orgLeaderName)
                .leaderTel(leaderTel)
                .build();
    }

}