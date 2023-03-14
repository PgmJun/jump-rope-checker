package com.jul.jumpropetornamentchecker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.dto.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;

    @Autowired private CompetitionService competitionService;

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
                .competitionName("test대회")
                .competitionHost("test")
                .competitionEndDate(LocalDate.now())
                .hostTel("02-1234-1234")
                .hostEmail("test@test.com")
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
                        .param("name","test대회"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("대회 데이터 findByName 실패 테스트")
    void testFailFindCompetitionDataByName() throws Exception {

        competitionService.saveCompetition(createTestCompDto());


        mockMvc.perform(get("/competition/find")
                        .param("name","ttest대회"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("대회 데이터 delete 테스트")
    void testDeleteCompetitionData() throws Exception {

        competitionService.saveCompetition(createTestCompDto());

        List<CompetitionResponseDto> testCompetitions = competitionService.findCompetitionInfoByName("test대회");

        competitionService.removeCompetitionData(testCompetitions.stream()
                .map(CompetitionResponseDto::competitionId)
                .collect(Collectors.toList()));

        mockMvc.perform(get("/competition/find")
                .param("name","test대회"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    private CompetitionRequestDto createTestCompDto() {
        return CompetitionRequestDto.builder()
                .competitionName("test대회")
                .competitionHost("test")
                .competitionStartDate(LocalDate.now())
                .competitionEndDate(LocalDate.now())
                .hostTel("02-1234-1234")
                .hostEmail("test@test.com")
                .build();
    }
}