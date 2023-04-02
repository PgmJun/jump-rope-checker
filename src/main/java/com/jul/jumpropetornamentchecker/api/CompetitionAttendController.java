package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.service.CompetitionAttendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping(value = "/attend", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
public class CompetitionAttendController extends ResponseEntityCreator {
    private final CompetitionAttendService cmptAttendService;

    @PostMapping("/add/single")
    @Operation(summary = "단일 선수 등록 API", description = "대회ID, 기관ID를 통해 선수 데이터를 등록합니다.")
    public ResponseEntity<?> registerSinglePlayer(@RequestBody CompetitionAttendRequestDto cmptAttendRequestDto) {
        Boolean saveResult = cmptAttendService.saveSinglePlayer(cmptAttendRequestDto);

        return getSaveDataResponseEntity(saveResult);
    }
/*
    @PostMapping("/add/csv")
    @Operation(summary = "CSV 파일 선수 등록 API", description = "대회ID, 기관ID와 CSV 파일을 사용하여 선수 데이터를 등록합니다.")
    public ResponseEntity<?> registerPlayerByCsvFile(@RequestParam(name = "playerList") MultipartFile playerDataFile, @RequestParam("orgId") Long organizationId) throws IOException {
        Boolean saveResult = playerService.savePlayerByCsv(playerDataFile, organizationId);

        return getSaveDataResponseEntity(saveResult);
    }

 */


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}
