package com.jul.jumpropetornamentchecker.dto.attend.eventAttend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventAttendUpdateDto {

    private Long cmptEventId;
    private int score;
}
