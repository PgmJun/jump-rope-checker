package com.jul.jumpropetornamentchecker.domain.attend;

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

    @Column
    private int score;


    public EventAttendResponseDto toDto() {
        return EventAttendResponseDto.builder()
                .eventAttendId(eventAttendId)
                .cmptEventId(competitionEvent.getCmptEventId())
                .cmptEventName(competitionEvent.getEvent().getEventName())
                .score(score)
                .build();
    }
}
