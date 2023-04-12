package com.jul.jumpropetornamentchecker.dto.attend.eventAttend;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EventAttendResponseDto {
    private Long eventAttendId;
    private Long cmptEventId;
    private String cmptEventName;
    private int score;
    private int grade;

}
