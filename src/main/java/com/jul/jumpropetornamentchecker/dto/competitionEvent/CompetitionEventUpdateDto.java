package com.jul.jumpropetornamentchecker.dto.competitionEvent;

public record CompetitionEventUpdateDto(Long cmptEventId, boolean isProceed,
                                        int firstGrandPrizePoint, int secondGrandPrizePoint, int thirdGrandPrizePoint, int fourthGrandPrizePoint, int fifthGrandPrizePoint,
                                        int partPoint, int fstPrizeStandard, int sndPrizeStandard, int trdPrizeStandard) {
}
