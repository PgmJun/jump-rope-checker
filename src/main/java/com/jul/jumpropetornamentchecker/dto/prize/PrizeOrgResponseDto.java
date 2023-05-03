package com.jul.jumpropetornamentchecker.dto.prize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrizeOrgResponseDto {
    //등수, 단체명, 종합점수, 1등선수수, 2등선수수, 3등선수수
    private int OrgGrade;
    private String OrgName;
    private int totalScore;
    private int fstPrizeNum;
    private int sndPrizeNum;
    private int trdPrizeNum;
}
