package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventUpdateDto;
import com.jul.jumpropetornamentchecker.service.CompetitionEventService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cmptEvent", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
public class CompetitionEventController extends ResponseEntityCreator {
    private final CompetitionEventService cmptEventService;

    @GetMapping("/find/{competitionId}")
    @Operation(summary = "대회종목 대회ID로 조회 API", description = "대회ID를 사용하여 대회 종목을 조회힌다.")
    public ResponseEntity<?> findCompetitionEventDataByCompetitionId(@PathVariable Long competitionId, @RequestParam(name = "type") String type) {
        List<CompetitionEventResponseDto> competitionEventDatum = cmptEventService.findCompetitionEventDataByCompetitionId(competitionId, type);
        return getFindDatumResponseEntity(competitionEventDatum);
    }

    @PatchMapping("/update")
    @Operation(summary = "대회종목 데이터 수정 API", description = "대회종목Id를 사용하여 대회종목 정보를 수정한다.")
    public ResponseEntity<?> updateCompetitionEventData(@RequestBody List<CompetitionEventUpdateDto> competitionEventUpdateDtos) {
        boolean updateResult = cmptEventService.updateCompetitionEventData(competitionEventUpdateDtos);

        return getUpdateDataResponseEntity(updateResult);
    }



    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}
