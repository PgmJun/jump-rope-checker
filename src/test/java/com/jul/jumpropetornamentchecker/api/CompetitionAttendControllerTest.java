//package com.jul.jumpropetornamentchecker.api;
//
//import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
//import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
//import com.jul.jumpropetornamentchecker.service.CompetitionAttendService;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CompetitionAttendControllerTest {
//
//    @Autowired
//    private CompetitionAttendService cmptAttendService;
//
//
//    @Test
//    @Transactional
//    @DisplayName("대회 참가 데이터 저장 테스트")
//    void saveTest() {
//        CompetitionAttendRequestDto competitionAttendRequestDto = CompetitionAttendRequestDto.builder()
//                .orgId(1L)
//                .cmptId(1L)
//                .cmptEventIds(List.of(2L, 3L, 4L))
//                .departId(1000L)
//                .playerGender("남")
//                .playerBirth("2000-09-07")
//                .playerName("ccc")
//                .playerTel("010-1234-1234")
//                .build();
//
//        Boolean saveResult = cmptAttendService.saveSinglePlayer(competitionAttendRequestDto);
//        Assertions.assertThat(saveResult).isTrue();
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("단체정보로 대회 참가 데이터 조회 테스트")
//    void findByOrganizationTest() {
//        CompetitionAttendRequestDto competitionAttendRequestDto = CompetitionAttendRequestDto.builder()
//                .orgId(1L)
//                .cmptId(1L)
//                .cmptEventIds(List.of(2L, 3L, 4L))
//                .departId(1000L)
//                .playerGender("남")
//                .playerBirth("2000-09-07")
//                .playerName("ccc")
//                .playerTel("010-1234-1234")
//                .build();
//
//        Boolean saveResult = cmptAttendService.saveSinglePlayer(competitionAttendRequestDto);
//        Assertions.assertThat(saveResult).isTrue();
//
//        List<CompetitionAttendResponseDto> playersByOrganizationId = cmptAttendService.findPlayersByOrgIdAndCmptId(1L, 1L);
//        Assertions.assertThat(playersByOrganizationId.isEmpty()).isFalse();
//    }
//
//
//}