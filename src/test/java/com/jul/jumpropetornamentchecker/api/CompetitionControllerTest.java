package com.jul.jumpropetornamentchecker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionUpdateDto;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CompetitionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CompetitionService competitionService;

    private final String compName = "test대회";
    private final String recordingSheetName = "test대회";
    private final String compHost = "test";
    private final String compHostTel = "02-1234-1234";
    private final String compHostEmail = "test@test.com";

    @Test
    @DisplayName("대회 데이터 add 테스트")
    void testInsertCompetitionData() throws Exception {

        String competitionDataJsonString = mapper.writeValueAsString(createTestCompDto());

        mockMvc.perform(post("/competition/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(competitionDataJsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("필수 데이터 누락이로 인한 add 실패 테스트")
    void testFailInsertCompetitionData() throws Exception {

        CompetitionRequestDto testCompetition = CompetitionRequestDto.builder()
                .competitionName(compName)
                .competitionHost(compHost)
                .competitionEndDate(LocalDateTime.now())
                .hostTel(compHostTel)
                .hostEmail(compHostEmail)
                .build();

        String competitionDataJsonString = mapper.writeValueAsString(testCompetition);

        mockMvc.perform(post("/competition/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(competitionDataJsonString))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("대회 데이터 findByName 테스트")
    void testFindCompetitionDataByName() throws Exception {

        competitionService.saveCompetition(createTestCompDto());


        mockMvc.perform(get("/competition/find")
                        .param("name", compName))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("대회 데이터 findByName 실패 테스트")
    void testFailFindCompetitionDataByName() throws Exception {

        competitionService.saveCompetition(createTestCompDto());
        String errorCompName = "ttest대회";

        mockMvc.perform(get("/competition/find")
                        .param("name", errorCompName))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("대회 데이터 delete 테스트")
    void testDeleteCompetitionData() throws Exception {

        competitionService.saveCompetition(createTestCompDto());

        List<CompetitionResponseDto> testCompetitions = competitionService.findCompetitionInfoByName(compName);

        competitionService.removeCompetitionData(testCompetitions.stream()
                .map(CompetitionResponseDto::competitionId)
                .collect(Collectors.toList()));

        mockMvc.perform(get("/competition/find")
                        .param("name", compName))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("대회 데이터 update 테스트")
    void testUpdateCompetitionData() {
        CompetitionRequestDto testCompDto = createTestCompDto();

        competitionService.saveCompetition(testCompDto);
        CompetitionResponseDto beforeCompData = competitionService.findCompetitionInfoByName(testCompDto.competitionName()).get(0);

        competitionService.updateCompetitionData(createUpdateCompDto(beforeCompData.competitionId()));
        CompetitionResponseDto afterCompData = competitionService.findCompetitionInfoById(beforeCompData.competitionId()).get();

        System.out.println("beforeCompData.competitionName() = " + beforeCompData.competitionName());
        System.out.println("afterCompData.competitionName() = " + afterCompData.competitionName());

        Assertions.assertThat(beforeCompData.competitionName()).isNotEqualTo(afterCompData.competitionName());
        Assertions.assertThat(afterCompData.competitionName()).isEqualTo("updated " + beforeCompData.competitionName());
    }


    private CompetitionRequestDto createTestCompDto() {
        return CompetitionRequestDto.builder()
                .competitionName(compName)
                .recordingSheetName(recordingSheetName)
                .competitionHost(compHost)
                .competitionStartDate(LocalDateTime.now())
                .competitionEndDate(LocalDateTime.now())
                .hostTel(compHostTel)
                .hostEmail(compHostEmail)
                .build();
    }

    private CompetitionUpdateDto createUpdateCompDto(Long id) {
        return CompetitionUpdateDto.builder()
                .competitionId(id)
                .competitionName("updated " + compName)
                .recordingSheetName(recordingSheetName)
                .competitionHost(compHost)
                .competitionStartDate(LocalDateTime.now())
                .competitionEndDate(LocalDateTime.now())
                .hostTel(compHostTel)
                .hostEmail(compHostEmail)
                .build();
    }
}