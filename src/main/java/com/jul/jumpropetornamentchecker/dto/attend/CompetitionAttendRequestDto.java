package com.jul.jumpropetornamentchecker.dto.attend;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompetitionAttendRequestDto {

    private Long cmptId;
    private Long orgId;
    private List<Long> cmptEventIds;
    private Long departId;
    private String playerName;
    private String playerGender;
    private String playerBirth;
    private String playerTel;
    private String playerAffiliation;

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
                ", playerAffiliation='" + playerAffiliation + '\'' +
                '}';
    }
}
