package com.jul.jumpropetornamentchecker.dto.competitionEvent;

public record CompetitionEventUpdateDto(Long cmptEventId, boolean isProceed,
                                        int partPoint,
                                        int fstPrizeStandard, int sndPrizeStandard, int trdPrizeStandard,
                                        int kinderFstStandard, int kinderSndStandard, int kinderTrdStandard,
                                        int e1FstStandard, int e1SndStandard, int e1TrdStandard,
                                        int e2FstStandard, int e2SndStandard, int e2TrdStandard,
                                        int e3FstStandard, int e3SndStandard, int e3TrdStandard,
                                        int e4FstStandard, int e4SndStandard, int e4TrdStandard,
                                        int e5FstStandard, int e5SndStandard, int e5TrdStandard,
                                        int e6FstStandard, int e6SndStandard, int e6TrdStandard,
                                        int mhFstStandard, int mhSndStandard, int mhTrdStandard,
                                        int cmFstStandard, int cmSndStandard, int cmTrdStandard) {
}
