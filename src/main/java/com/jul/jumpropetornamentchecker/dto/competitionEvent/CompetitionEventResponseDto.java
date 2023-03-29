package com.jul.jumpropetornamentchecker.dto.competitionEvent;

import lombok.Builder;

@Builder
public record CompetitionEventResponseDto(Long cmptEventId, String competitionName, String eventName, boolean isProceed,
                                          int partPoint, int fstPrizeStandard, int sndPrizeStandard, int trdPrizeStandard) {
}