package com.jul.jumpropetornamentchecker.dto.competitionEvent;

public record CompetitionEventUpdateDto(Long cmptEventId, boolean isProceed,
                                        int partPoint, int fstPrizeStandard, int sndPrizeStandard, int trdPrizeStandard) {
}
