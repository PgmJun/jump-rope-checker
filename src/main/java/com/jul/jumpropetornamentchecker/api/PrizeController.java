package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.domain.OrgPrizeData;
import com.jul.jumpropetornamentchecker.dto.prize.PrizePrintRequestDto;
import com.jul.jumpropetornamentchecker.dto.prize.PrizeResponseDto;
import com.jul.jumpropetornamentchecker.service.PrizeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "prize", produces = "application/json; charset=UTF8")
public class PrizeController extends ResponseEntityCreator {
    private final PrizeService prizeService;

    @GetMapping("/{cmptId}")
    public ResponseEntity<?> getCompetitionPrizedPlayerList(@PathVariable Long cmptId) {
        List<PrizeResponseDto> competitionPrizeDatum = prizeService.getCompetitionPrizeData(cmptId);

        return getFindDatumResponseEntity(competitionPrizeDatum);
    }

    @GetMapping("/{cmptId}/{orgId}")
    public ResponseEntity<?> getCompetitionPrizedPlayerListByOrgId(@PathVariable Long cmptId, @PathVariable Long orgId) {
        List<PrizeResponseDto> competitionPrizeDatum = prizeService.getCompetitionPrizeDataByOrgId(cmptId, orgId);

        return getFindDatumResponseEntity(competitionPrizeDatum);
    }

    @PatchMapping("/changePrintState")
    public ResponseEntity<?> changePrintState(@RequestBody PrizePrintRequestDto requestDto) {
        boolean updateResult = prizeService.changePrintStates(requestDto.getEventAttendIds());

        return getUpdateDataResponseEntity(updateResult);
    }

    @GetMapping("/org/{cmptId}")
    @Operation(summary = "대회의 단체 성적 결과 조회 API")
    public ResponseEntity<?> getOrganizationPrizeData(@PathVariable Long cmptId) {
        List<OrgPrizeData> result = prizeService.getOrganizationPrizeDataByCmptId(cmptId);

        return getFindDatumResponseEntity(result);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}
