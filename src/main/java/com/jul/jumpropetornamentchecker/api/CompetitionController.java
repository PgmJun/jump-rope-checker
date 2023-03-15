package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.dto.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionUpdateDto;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competition")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @PostMapping("/add")
    @Operation(summary = "대회 추가 API", description = "대회 정보를 입력하여 대회를 추가합니다.")
    public ResponseEntity<?> registerCompetitionData(@RequestBody CompetitionRequestDto competitionDto) {
        Boolean saveResult = competitionService.saveCompetition(competitionDto);

        return (saveResult) ?
                new ResponseEntity<>(competitionDto.competitionName() + " 경기가 등록되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("경기 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/all")
    @Operation(summary = "대회 전체 조회 API", description = "전체 대회 정보를 조회합니다.")
    public ResponseEntity<?> findAllCompetitionData() {
        List<CompetitionResponseDto> competitionInfo = competitionService.findAllCompetitionInfo();
        return (competitionInfo.isEmpty()) ?
                new ResponseEntity<>("no competition data", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(competitionInfo, HttpStatus.OK);
    }

    @GetMapping("/find")
    @Operation(summary = "대회 이름 조회 API", description = "대회명을 사용하여 대회 정보를 조회합니다.")
    public ResponseEntity<?> findCompetitionDataByName(@RequestParam("name") String competitionName) {
        List<CompetitionResponseDto> competitionInfo = competitionService.findCompetitionInfoByName(competitionName);
        return (competitionInfo.isEmpty()) ?
                new ResponseEntity<>("no NAME: " + competitionName + " competition data", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(competitionInfo, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "대회 ID 조회 API", description = "대회ID를 사용하여 대회 정보를 조회합니다.")
    public ResponseEntity<?> findCompetitionDataByName(@PathVariable("id") Long competitionId) {
        Optional<CompetitionResponseDto> competitionInfo = competitionService.findCompetitionInfoById(competitionId);
        return (competitionInfo.isEmpty()) ?
                new ResponseEntity<>("no ID." + competitionId + " competition data", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(competitionInfo, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "대회 정보 삭제 API", description = "대회ID List를 통해 대회 정보를 삭제합니다.")
    public ResponseEntity<?> deleteCompetitionDataById(@RequestBody List<Long> competitionIds) {
        return (competitionService.removeCompetitionData(competitionIds)) ?
                new ResponseEntity<>("competition datum are removed", HttpStatus.OK) :
                new ResponseEntity<>("competition datum are not removed", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    @Operation(summary = "대회 정보 수정 API", description = "대회 정보를 업데이트합니다.")
    public ResponseEntity<?> updateCompetitionData(@RequestBody CompetitionUpdateDto competitionUpdateDtos) {
        return (competitionService.updateCompetitionData(competitionUpdateDtos)) ?
                new ResponseEntity<>("competition datum is updated", HttpStatus.OK) :
                new ResponseEntity<>("competition datum are fail to update", HttpStatus.BAD_REQUEST);
    }

}
