package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendPlayerResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendPlayerResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendUpdateDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.service.CompetitionAttendService;
import com.jul.jumpropetornamentchecker.service.EventAttendService;
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
@RequestMapping(value = "/attend", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
public class CompetitionAttendController extends ResponseEntityCreator {
    private final CompetitionAttendService cmptAttendService;
    private final EventAttendService eventAttendService;

    @PostMapping(value = "/add/single", produces = "application/json; charset=UTF8")
    @Operation(summary = "단일 선수 등록 API", description = "대회ID, 기관ID를 통해 선수 데이터를 등록합니다.")
    public ResponseEntity<?> registerSinglePlayer(@RequestBody CompetitionAttendRequestDto cmptAttendRequestDto) {
        Boolean saveResult = cmptAttendService.saveSinglePlayer(cmptAttendRequestDto);

        return getSaveDataResponseEntity(saveResult);
    }

    @DeleteMapping("/delete/player/{cmptAttendId}")
    @Operation(summary = "참가 정보 삭제 API", description = "대회참가ID를 사용하여 참가 선수 데이터를 삭제합니다.")
    public ResponseEntity<?> deletePlayerByCmptAttendId(@PathVariable String cmptAttendId) {
        Boolean removeResult = cmptAttendService.removePlayerByCmptAttendId(cmptAttendId);

        return getRemoveDataResponseEntity(removeResult);
    }

    @DeleteMapping("/delete/players")
    @Operation(summary = "단체 참가 정보 삭제 API", description = "대회ID, 기관ID를 사용하여 대회에 참가하는 단체의 선수 데이터를 삭제합니다.")
    public ResponseEntity<?> deletePlayers(@RequestParam(name = "cmptId") Long cmptId, @RequestParam("orgId") Long orgId) {
        Boolean removeResult = cmptAttendService.removePlayerByCmptIdAndOrgId(cmptId, orgId);

        return getRemoveDataResponseEntity(removeResult);
    }

    @GetMapping("/create/form")
    @Operation(summary = "CSV 파일 선수 등록 신청서 요청 API", description = "대회ID, 기관ID를 사용하여 대회 신청서 양식을 요청합니다.")
    public ResponseEntity<?> createPlayerAttendForm(HttpServletResponse response, @RequestParam(name = "cmptId") Long cmptId, @RequestParam("orgId") Long orgId) {
        boolean competitionAttendForm = cmptAttendService.createCompetitionAttendForm(response, cmptId, orgId);
        return createCompetitionAttendFormResponseEntity(competitionAttendForm);
    }

    @PostMapping("/add/form")
    @Operation(summary = "엑셀 파일 선수 등록 API", description = "엑셀 파일을 사용하여 선수 데이터를 등록합니다.")
    public ResponseEntity<?> registerPlayerByExcelFile(@RequestParam(name = "attendForm") MultipartFile attendForm) {
        boolean saveResult = cmptAttendService.savePlayerByAttendForm(attendForm);
        return getSaveDataResponseEntity(saveResult);
    }


    @GetMapping("/find/player")
    @Operation(summary = "단체별 대회 참가 선수 조회 API", description = "대회ID, 단체ID를 사용하여 대회 참가 선수를 조회합니다.")
    public ResponseEntity<?> findAttendDataByOrgIdAndCmptId(@RequestParam(name = "orgId") Long orgId, @RequestParam(name = "cmptId") Long cmptId) {
        List<CompetitionAttendPlayerResponseDto> cmptAttendPlayerDatum = cmptAttendService.findPlayersByOrgIdAndCmptId(orgId, cmptId);

        return getFindDatumResponseEntity(cmptAttendPlayerDatum);
    }

    @GetMapping("/find/player/cmpt/{cmptId}")
    @Operation(summary = "대회 참가 선수 조회 API", description = "대회ID를 사용하여 대회 참가 선수를 조회합니다.")
    public ResponseEntity<?> findAttendDataByOrgId(@PathVariable(name = "cmptId") Long cmptId) {
        List<CompetitionAttendPlayerResponseDto> cmptAttendPlayerDatum = cmptAttendService.findPlayersByCmptId(cmptId);

        return getFindDatumResponseEntity(cmptAttendPlayerDatum);
    }

    @GetMapping("/find/player/cmptEvent/{cmptEventId}")
    @Operation(summary = "대회 종목 참가 선수 조회 API", description = "대회종목ID를 사용하여 대회종목에 참가하는 선수 데이터를 조회합니다.")
    public ResponseEntity<?> findCmptEventAttendPlayerData(@PathVariable Long cmptEventId) {
        List<EventAttendPlayerResponseDto> cmptAttendPlayerDatum = cmptAttendService.findCmptEventAttendPlayersByCmptEventId(cmptEventId);

        return getFindDatumResponseEntity(cmptAttendPlayerDatum);
    }


    @GetMapping("/find/org/{cmptId}")
    @Operation(summary = "대회 참가 단체 조회 API", description = "대회ID를 사용하여 대회 참가 단체를 조회합니다.")
    public ResponseEntity<?> findAttendOrganizationDataByCmptId(@PathVariable Long cmptId) {
        List<OrganizationResponseDto> organizationDatum = cmptAttendService.findOrganizationsByCmptId(cmptId);

        return getFindDatumResponseEntity(organizationDatum);
    }

    @GetMapping("/find/attendEvent/{cmptAttendId}")
    @Operation(summary = "선수의 참가 종목 정보 조회 API", description = "대회참가ID를 사용하여 참가선수의 참가종목 별 데이터를 조회합니다.")
    public ResponseEntity<?> findCompetitionEventDataByCmptAttendId(@PathVariable String cmptAttendId) {
        List<EventAttendPlayerResponseDto> eventAttendPlayerDatum = cmptAttendService.findEventAttendPlayerDataByCmptAttendId(cmptAttendId);

        return getFindDatumResponseEntity(eventAttendPlayerDatum);
    }

    @PatchMapping("/update/eventScore/{cmptAttendId}")
    @Operation(summary = "선수의 참가 종목 점수 갱신 API", description = "대회참가ID와 대회종목ID를 사용하여 참가선수의 참가종목 점수를 갱신합니다.")
    public ResponseEntity<?> updatePlayerEventScore(@PathVariable(name = "cmptAttendId") String cmptAttendId, @RequestBody EventAttendUpdateDto updateData) {
        Boolean updateResult = eventAttendService.updatePlayerEventScoreByCompetitionAttendId(cmptAttendId, updateData);

        return getUpdateDataResponseEntity(updateResult);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}