package com.jul.jumpropetornamentchecker.domain.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
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
}
