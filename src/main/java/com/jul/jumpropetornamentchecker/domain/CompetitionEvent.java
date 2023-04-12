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
    private int partPoint; // 참가 점수

    @Column
    private int fstPrizeStandard; // 금상을 받으면 score에 +할 값

    @Column
    private int sndPrizeStandard; // 은상 수상시 score에 +할 값

    @Column
    private int trdPrizeStandard; // 동상 수상시 score에 +할 값

    //유치부 금상,은상,동상 기준
    @Column
    private int kinderFstStandard;

    @Column
    private int kinderSndStandard;

    @Column
    private int kinderTrdStandard;

    //초등1부 금상,은상,동상 기준
    @Column
    private int e1FstStandard;

    @Column
    private int e1SndStandard;

    @Column
    private int e1TrdStandard;

    //초등3부 금상,은상,동상 기준
    @Column
    private int e2FstStandard;

    @Column
    private int e2SndStandard;

    @Column
    private int e2TrdStandard;

    //초등3부 금상,은상,동상 기준
    @Column
    private int e3FstStandard;

    @Column
    private int e3SndStandard;

    @Column
    private int e3TrdStandard;

    //초등4부 금상,은상,동상 기준
    @Column
    private int e4FstStandard;

    @Column
    private int e4SndStandard;

    @Column
    private int e4TrdStandard;

    //초등5부 금상,은상,동상 기준
    @Column
    private int e5FstStandard;

    @Column
    private int e5SndStandard;

    @Column
    private int e5TrdStandard;

    //초등6부 금상,은상,동상 기준
    @Column
    private int e6FstStandard;

    @Column
    private int e6SndStandard;

    @Column
    private int e6TrdStandard;

    //중고등부 금상,은상,동상 기준
    @Column
    private int mhFstStandard;

    @Column
    private int mhSndStandard;

    @Column
    private int mhTrdStandard;

    //일반부 금상,은상,동상 기준
    @Column
    private int cmFstStandard;

    @Column
    private int cmSndStandard;

    @Column
    private int cmTrdStandard;




    public CompetitionEventResponseDto toDto() {
        return CompetitionEventResponseDto.builder()
                .cmptEventId(cmptEventId)
                .competitionName(competition.getCompetitionName())
                .eventName(event.getEventName())
                .eventId(event.getEventId())
                .isProceed(isProceed)
                .partPoint(partPoint)
                .fstPrizeStandard(fstPrizeStandard)
                .sndPrizeStandard(sndPrizeStandard)
                .trdPrizeStandard(trdPrizeStandard)

                .kinderFstStandard(kinderFstStandard)
                .kinderSndStandard(kinderSndStandard)
                .kinderTrdStandard(kinderTrdStandard)

                .e1FstStandard(e1FstStandard)
                .e1SndStandard(e1SndStandard)
                .e1TrdStandard(e1TrdStandard)

                .e2FstStandard(e2FstStandard)
                .e2SndStandard(e2SndStandard)
                .e2TrdStandard(e2TrdStandard)

                .e3FstStandard(e3FstStandard)
                .e3SndStandard(e3SndStandard)
                .e3TrdStandard(e3TrdStandard)

                .e4FstStandard(e4FstStandard)
                .e4SndStandard(e4SndStandard)
                .e4TrdStandard(e4TrdStandard)

                .e5FstStandard(e5FstStandard)
                .e5SndStandard(e5SndStandard)
                .e5TrdStandard(e5TrdStandard)

                .e6FstStandard(e6FstStandard)
                .e6SndStandard(e6SndStandard)
                .e6TrdStandard(e6TrdStandard)

                .mhFstStandard(mhFstStandard)
                .mhSndStandard(mhSndStandard)
                .mhTrdStandard(mhTrdStandard)

                .cmFstStandard(cmFstStandard)
                .cmSndStandard(cmSndStandard)
                .cmTrdStandard(cmTrdStandard)
                .build();
    }

    public void changeData(CompetitionEventUpdateDto updateDto) {
        this.isProceed = updateDto.isProceed();
        this.partPoint = updateDto.partPoint();

        this.fstPrizeStandard = updateDto.fstPrizeStandard();
        this.sndPrizeStandard = updateDto.sndPrizeStandard();
        this.trdPrizeStandard = updateDto.trdPrizeStandard();

        this.kinderFstStandard = updateDto.kinderFstStandard();
        this.kinderSndStandard = updateDto.kinderSndStandard();
        this.kinderTrdStandard = updateDto.kinderTrdStandard();

        this.e1FstStandard = updateDto.e1FstStandard();
        this.e1SndStandard = updateDto.e1SndStandard();
        this.e1TrdStandard = updateDto.e1TrdStandard();

        this.e2FstStandard = updateDto.e2FstStandard();
        this.e2SndStandard = updateDto.e2SndStandard();
        this.e2TrdStandard = updateDto.e2TrdStandard();

        this.e3FstStandard = updateDto.e3FstStandard();
        this.e3SndStandard = updateDto.e3SndStandard();
        this.e3TrdStandard = updateDto.e3TrdStandard();

        this.e4FstStandard = updateDto.e4FstStandard();
        this.e4SndStandard = updateDto.e4SndStandard();
        this.e4TrdStandard = updateDto.e4TrdStandard();

        this.e5FstStandard = updateDto.e5FstStandard();
        this.e5SndStandard = updateDto.e5SndStandard();
        this.e5TrdStandard = updateDto.e5TrdStandard();

        this.e6FstStandard = updateDto.e6FstStandard();
        this.e6SndStandard = updateDto.e6SndStandard();
        this.e6TrdStandard = updateDto.e6TrdStandard();

        this.mhFstStandard = updateDto.mhFstStandard();
        this.mhSndStandard = updateDto.mhSndStandard();
        this.mhTrdStandard = updateDto.mhTrdStandard();

        this.cmFstStandard = updateDto.cmFstStandard();
        this.cmSndStandard = updateDto.cmSndStandard();
        this.cmTrdStandard = updateDto.cmTrdStandard();
    }

}
