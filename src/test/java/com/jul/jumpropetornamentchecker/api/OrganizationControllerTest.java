package com.jul.jumpropetornamentchecker.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationUpdateDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    @DisplayName("단체 정보 삭제 기능 테스트")
    void testDeleteOrganizationDatumByIds() throws Exception {
        OrganizationRequestDto testDto = createTestDto();
        organizationService.saveOrganization(testDto);

        OrganizationResponseDto testOrgData = organizationService.findOrganizationByName(testDto.orgName()).get(0);

        String orgIdJson = objectMapper.writeValueAsString(testOrgData.orgId());

        mockMvc.perform(delete("/organization/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orgIdJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("단체 정보 수정 기능 테스트")
    void testUpdateOrganizationData() {
        OrganizationRequestDto testDto = createTestDto();
        organizationService.saveOrganization(testDto);

        OrganizationResponseDto organizationData = organizationService.findOrganizationByName(testDto.orgName()).get(0);
        organizationService.updateOrganizationData(createUpdateDto(organizationData.orgId()));

        Organization updatedOrganizationData = organizationService.findOrganizationById(organizationData.orgId()).get();

        assertThat(organizationData.orgName()).isNotEqualTo(updatedOrganizationData.toDto().orgName());
        assertThat(updatedOrganizationData.toDto().orgName()).isEqualTo("updated"+organizationData.orgName());



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

    private OrganizationUpdateDto createUpdateDto(Long id) {
        return OrganizationUpdateDto.builder()
                .orgId(id)
                .orgName("updated" + orgName)
                .orgEmail(orgEmail)
                .orgTel(orgTel)
                .orgLeaderName(orgLeaderName)
                .leaderTel(leaderTel)
                .build();
    }

}