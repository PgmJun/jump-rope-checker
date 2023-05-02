package com.jul.jumpropetornamentchecker.domain.attend;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import jakarta.persistence.*;
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
public class EventAttend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventAttendId;

    @OneToOne
    @JoinColumn(name = "cmptEventId")
    private CompetitionEvent competitionEvent;

    @ManyToOne
    @JoinColumn(name = "cmptAttendId")
    private CompetitionAttend competitionAttend;

    @ManyToOne
    @JoinColumn(name = "competitionId")
    private Competition  competition;

    @Column
    private int score; // 줄넘기 점수 or 갯수

    @Column
    private int grade; // 대회종목 수상내역 기록 - 금(3),은(2),동(1)

    @Column
    private boolean isPrinted; // 출력 여부



    public EventAttendResponseDto toDto() {
        return EventAttendResponseDto.builder()
                .eventAttendId(eventAttendId)
                .cmptEventId(competitionEvent.getCmptEventId())
                .cmptEventName(competitionEvent.getEvent().getEventName())
                .score(score)
                .grade(grade)
                .isPrinted(isPrinted)
                .build();
    }

    public void changePrintState() {
        isPrinted = Boolean.TRUE;
    }
}
