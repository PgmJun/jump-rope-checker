package com.jul.jumpropetornamentchecker.domain;

import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmptEventId;

    @ManyToOne
    @JoinColumn(name = "competitionId")
    private Competition competition;

    @OneToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @Column
    @NotNull
    private Boolean isProceed;

    @Column
    @NotNull
    private int partPoint;

    @Column
    @NotNull
    private int fstPrizeStandard;

    @Column
    @NotNull
    private int sndPrizeStandard;

    @Column
    @NotNull
    private int trdPrizeStandard;


    public CompetitionEventResponseDto toDto() {
        return CompetitionEventResponseDto.builder()
                .cmptEventId(cmptEventId)
                .competitionName(competition.getCompetitionName())
                .eventName(event.getEventName())
                .isProceed(isProceed)
                .partPoint(partPoint)
                .fstPrizeStandard(fstPrizeStandard)
                .sndPrizeStandard(sndPrizeStandard)
                .trdPrizeStandard(trdPrizeStandard)
                .build();
    }

    public void changeData(CompetitionEventUpdateDto updateDto) {
        this.isProceed = updateDto.isProceed();
        this.partPoint = updateDto.partPoint();
        this.fstPrizeStandard = updateDto.fstPrizeStandard();
        this.sndPrizeStandard = updateDto.sndPrizeStandard();
        this.trdPrizeStandard = updateDto.trdPrizeStandard();
    }
}
