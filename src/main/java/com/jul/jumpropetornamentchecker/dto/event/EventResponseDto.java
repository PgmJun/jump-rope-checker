package com.jul.jumpropetornamentchecker.dto.event;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventResponseDto {
    private Long eventId;
    private String eventName;
    private Boolean isGroupGame;
}
