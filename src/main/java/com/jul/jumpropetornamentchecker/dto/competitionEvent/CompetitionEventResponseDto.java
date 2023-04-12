package com.jul.jumpropetornamentchecker.dto.competitionEvent;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class CompetitionEventResponseDto {
    private Long cmptEventId;
    private String competitionName;
    private String eventName;
    private Boolean isProceed;
    private Long eventId;
    private int partPoint;
    private int fstPrizeStandard;
    private int sndPrizeStandard;
    private int trdPrizeStandard;
    private int kinderFstStandard;
    private int kinderSndStandard;
    private int kinderTrdStandard;
    private int e1FstStandard;
    private int e1SndStandard;
    private int e1TrdStandard;
    private int e2FstStandard;
    private int e2SndStandard;
    private int e2TrdStandard;
    private int e3FstStandard;
    private int e3SndStandard;
    private int e3TrdStandard;
    private int e4FstStandard;
    private int e4SndStandard;
    private int e4TrdStandard;
    private int e5FstStandard;
    private int e5SndStandard;
    private int e5TrdStandard;
    private int e6FstStandard;
    private int e6SndStandard;
    private int e6TrdStandard;
    private int mhFstStandard;
    private int mhSndStandard;
    private int mhTrdStandard;
    private int cmFstStandard;
    private int cmSndStandard;
    private int cmTrdStandard;

    public Map<Long, List<Integer>> getStandardValue() {
        Map<Long, List<Integer>> standardValueMap = new HashMap<>();
        standardValueMap.put(0L, List.of(kinderTrdStandard, kinderSndStandard, kinderFstStandard));
        standardValueMap.put(1L, List.of(e1TrdStandard, e1SndStandard, e1FstStandard));
        standardValueMap.put(2L, List.of(e2TrdStandard, e2SndStandard, e2FstStandard));
        standardValueMap.put(3L, List.of(e3TrdStandard, e3SndStandard, e3FstStandard));
        standardValueMap.put(4L, List.of(e4TrdStandard, e4SndStandard, e4FstStandard));
        standardValueMap.put(5L, List.of(e5TrdStandard, e5SndStandard, e5FstStandard));
        standardValueMap.put(6L, List.of(e6TrdStandard, e6SndStandard, e6FstStandard));
        standardValueMap.put(7L, List.of(mhTrdStandard, mhSndStandard, mhFstStandard));
        standardValueMap.put(8L, List.of(cmTrdStandard, cmSndStandard, cmFstStandard));

        return standardValueMap;
    }
}