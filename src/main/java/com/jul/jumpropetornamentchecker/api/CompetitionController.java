package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionUpdateDto;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/competition", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
public class CompetitionController extends ResponseEntityCreator {
    private final CompetitionService competitionService;

    @PostMapping("/add")
    @Operation(summary = "대회 추가 API", description = "대회 정보를 입력하여 대회를 추가합니다.")
    public ResponseEntity<?> registerCompetitionData(@RequestBody CompetitionRequestDto competitionDto) {
        Boolean saveResult = competitionService.saveCompetition(competitionDto);

        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping("/find/all")
    @Operation(summary = "대회 전체 조회 API", description = "전체 대회 정보를 조회합니다.")
    public ResponseEntity<?> findAllCompetitionData() {
        List<CompetitionResponseDto> competitionDatum = competitionService.findAllCompetitionInfo();

        return getFindDatumResponseEntity(competitionDatum);
    }

    @GetMapping("/{cmptId}")
    @Operation(summary = "대회 ID 조회 API", description = "대회ID를 사용하여 대회 정보를 조회합니다.")
    public ResponseEntity<?> findCompetitionDataById(@PathVariable("cmptId") Long cmptId) {
        Optional<CompetitionResponseDto> competitionData = competitionService.findCompetitionInfoById(cmptId);

        return getFindDataResponseEntity(competitionData);
    }

    @GetMapping(value = "/find")
    @Operation(summary = "대회 이름 조회 API", description = "대회명을 사용하여 대회 정보를 조회합니다.")
    public ResponseEntity<?> findCompetitionDataByName(@RequestParam("name") String competitionName) {
        List<CompetitionResponseDto> competitionDatum = competitionService.findCompetitionInfoByName(competitionName);

        return getFindDatumResponseEntity(competitionDatum);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "대회 정보 삭제 API", description = "대회ID List를 통해 대회 정보를 삭제합니다.")
    public ResponseEntity<?> deleteCompetitionDataById(@RequestBody List<Long> competitionIds) {
        Boolean removeResult = competitionService.removeCompetitionData(competitionIds);

        return getRemoveDataResponseEntity(removeResult);
    }

    @PutMapping("/update")
    @Operation(summary = "대회 정보 수정 API", description = "대회 정보를 업데이트합니다.")
    public ResponseEntity<?> updateCompetitionData(@RequestBody CompetitionUpdateDto competitionUpdateDto) {
        boolean updateResult = competitionService.updateCompetitionData(competitionUpdateDto);

        return getUpdateDataResponseEntity(updateResult);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> data) {
        return (data.isEmpty()) ?
                new ResponseEntity<>("데이터를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(((Competition) data.get()).toDto(), HttpStatus.OK);
    }
}
