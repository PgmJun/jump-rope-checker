package com.jul.jumpropetornamentchecker.dto.prize;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PrizePrintRequestDto {
    private List<Long> eventAttendIds;

    public List<Long> getEventAttendIds() {
        return eventAttendIds;
    }
}
