package com.jul.jumpropetornamentchecker.domain;

import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CmptEventResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmptEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competitionId")
    private Competition competition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    private Event event;

    @Column
    private Boolean isProceed;

    @Column
    private int partPoint;

    @Column
    private int fstPrizeStandard;

    @Column
    private int sndPrizeStandard;

    @Column
    private int trdPrizeStandard;


    public CmptEventResponseDto toDto() {
        return CmptEventResponseDto.builder()
                .cmptEventId(cmptEventId)
                .competitionName(competition.getCompetitionName())
                .eventName(event.getEventName())
                .partPoint(partPoint)
                .fstPrizeStandard(fstPrizeStandard)
                .sndPrizeStandard(sndPrizeStandard)
                .trdPrizeStandard(trdPrizeStandard)
                .build();
    }
}
