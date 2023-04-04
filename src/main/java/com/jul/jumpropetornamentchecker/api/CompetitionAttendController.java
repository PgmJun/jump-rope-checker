package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
import com.jul.jumpropetornamentchecker.service.CompetitionAttendService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/attend")
@RequiredArgsConstructor
public class CompetitionAttendController extends ResponseEntityCreator {
    private final CompetitionAttendService cmptAttendService;

    @PostMapping(value = "/add/single", produces = "application/json; charset=UTF8")
    @Operation(summary = "단일 선수 등록 API", description = "대회ID, 기관ID를 통해 선수 데이터를 등록합니다.")
    public ResponseEntity<?> registerSinglePlayer(@RequestBody CompetitionAttendRequestDto cmptAttendRequestDto) {
        Boolean saveResult = cmptAttendService.saveSinglePlayer(cmptAttendRequestDto);

        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping(value = "/create/form", produces = "application/vnd.ms-excel; charset=UTF8")
    @Operation(summary = "CSV 파일 선수 등록 신청서 요청 API", description = "대회ID, 기관ID를 사용하여 대회 신청서 양식을 요청합니다.")
    public ResponseEntity<?> createPlayerAttendForm(HttpServletResponse response, @RequestParam(name = "cmptId") Long cmptId, @RequestParam("orgId") Long orgId) throws IOException {
        boolean competitionAttendForm = cmptAttendService.createCompetitionAttendForm(response, cmptId, orgId);
        return createCompetitionAttendFormResponseEntity(competitionAttendForm);
    }

    @PostMapping("/add/form")
    @Operation(summary = "엑셀 파일 선수 등록 API", description = "엑셀 파일을 사용하여 선수 데이터를 등록합니다.")
    public ResponseEntity<?> registerPlayerByCsvFile(@RequestParam(name = "attendForm") MultipartFile attendForm) {
        boolean saveResult = cmptAttendService.savePlayerByAttendForm(attendForm);
        return getSaveDataResponseEntity(saveResult);
    }


    @GetMapping("/find")
    @Operation(summary = "참가 선수 찾기 API", description = "대회ID, 기관ID를 사용하여 대회 참가 선수를 조회합니다.")
    public ResponseEntity<?> findAttendDataByOrganizationId(@RequestParam(name = "orgId") Long orgId, @RequestParam(name = "cmptId") Long cmptId) {
        List<CompetitionAttendResponseDto> cmptAttendDatum = cmptAttendService.findPlayersByOrgIdAndCmptId(orgId, cmptId);

        return getFindDatumResponseEntity(cmptAttendDatum);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}
