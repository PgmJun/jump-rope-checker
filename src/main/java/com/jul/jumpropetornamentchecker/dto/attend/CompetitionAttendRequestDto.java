package com.jul.jumpropetornamentchecker.dto.attend;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CompetitionAttendRequestDto {

    private Long cmptId;
    private Long orgId;
    private List<Long> cmptEventIds;
    private Long departId;
    private String playerName;
    private String playerGender;
    private String playerBirth;
    private String playerTel;

    @Override
    public String toString() {
        return "CompetitionAttendRequestDto{" +
                "cmptId=" + cmptId +
                ", orgId=" + orgId +
                ", cmptEventIds=" + cmptEventIds +
                ", departId=" + departId +
                ", playerName='" + playerName + '\'' +
                ", playerGender='" + playerGender + '\'' +
                ", playerBirth='" + playerBirth + '\'' +
                ", playerTel='" + playerTel + '\'' +
                '}';
    }
}
