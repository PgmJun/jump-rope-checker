package com.jul.jumpropetornamentchecker.domain.event;

import com.jul.jumpropetornamentchecker.dto.event.EventResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Event {

    @Id
    private Long eventId;

    @Column
    private String eventName;

    @Column
    private Boolean isGroupEvent;


    public static Event from(EventData eventData) {
        return Event.builder()
                .eventId(eventData.getEventId())
                .eventName(eventData.getEventName())
                .isGroupEvent(eventData.getIsGroupGame())
                .build();
    }

    public EventResponseDto toDto() {
        return EventResponseDto.builder()
                .eventId(eventId)
                .eventName(eventName)
                .isGroupGame(isGroupEvent)
                .build();
    }
}
