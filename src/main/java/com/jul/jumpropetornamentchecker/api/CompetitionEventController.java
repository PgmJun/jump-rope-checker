package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CmptEventResponseDto;
import com.jul.jumpropetornamentchecker.service.CompetitionEventService;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cmptEvent")
@RequiredArgsConstructor
public class CompetitionEventController extends ResponseEntityCreator {
    private final CompetitionEventService cmptEventService;
    private final CompetitionService competitionService;


    @GetMapping("find/{competitionId}")
    @Operation(summary = "대회종목 대회ID로 조회 API", description = "대회ID를 사용하여 대회 종목을 조회힌다.")
    public ResponseEntity<?> findCompetitionEventDataByCompetitionId(@PathVariable Long competitionId) {
        List<CmptEventResponseDto> competitionEventDatum = cmptEventService.findCompetitionEventDataByCompetitionId(competitionId);
        return getFindDatumResponseEntity(competitionEventDatum);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}
