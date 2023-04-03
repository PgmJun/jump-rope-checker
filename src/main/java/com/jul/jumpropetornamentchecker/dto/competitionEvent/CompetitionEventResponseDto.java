package com.jul.jumpropetornamentchecker.dto.competitionEvent;

import com.jul.jumpropetornamentchecker.domain.event.Event;
import lombok.Builder;

@Builder
public record CompetitionEventResponseDto(Long cmptEventId, String competitionName, String eventName, Boolean isProceed, Long eventId,
                                          int firstGrandPrizePoint, int secondGrandPrizePoint, int thirdGrandPrizePoint, int fourthGrandPrizePoint, int fifthGrandPrizePoint,
                                          int partPoint, int fstPrizeStandard, int sndPrizeStandard, int trdPrizeStandard) {
}
