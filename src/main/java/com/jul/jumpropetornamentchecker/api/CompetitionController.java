package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.dto.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.CompetitionUpdateDto;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> registerCompetitionData(@RequestBody CompetitionRequestDto competitionDto) {
        Boolean saveResult = competitionService.saveCompetition(competitionDto);

        return (saveResult) ?
                new ResponseEntity<>(competitionDto.competitionName() + " 경기가 등록되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("경기 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAllCompetitionData() {
        List<CompetitionResponseDto> competitionInfo = competitionService.findAllCompetitionInfo();
        return (competitionInfo.isEmpty()) ?
                new ResponseEntity<>("no competition data", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(competitionInfo, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findCompetitionDataByName(@RequestParam("name") String competitionName) {
        List<CompetitionResponseDto> competitionInfo = competitionService.findCompetitionInfoByName(competitionName);
        return (competitionInfo.isEmpty()) ?
                new ResponseEntity<>("no NAME: " + competitionName + " competition data", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(competitionInfo, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findCompetitionDataByName(@PathVariable("id") Long competitionId) {
        Optional<CompetitionResponseDto> competitionInfo = competitionService.findCompetitionInfoById(competitionId);
        return (competitionInfo.isEmpty()) ?
                new ResponseEntity<>("no ID." + competitionId + " competition data", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(competitionInfo, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCompetitionDataById(@RequestBody List<Long> competitionIds) {
        return (competitionService.removeCompetitionData(competitionIds)) ?
                new ResponseEntity<>("competition datum are removed", HttpStatus.OK) :
                new ResponseEntity<>("competition datum are not removed", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCompetitionData(@RequestBody List<CompetitionUpdateDto> competitionUpdateDtos) {
        return (competitionService.updateCompetitionData(competitionUpdateDtos)) ?
                new ResponseEntity<>("competition datum is updated",HttpStatus.OK) :
                new ResponseEntity<>("competition datum are fail to update", HttpStatus.BAD_REQUEST);
    }

}
