package com.jul.jumpropetornamentchecker.domain;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class OrgPrizeData implements Comparable<OrgPrizeData>{
    private String orgName;
    private int totalScore;
    private int fstPrizeCnt;
    private int sndPrizeCnt;
    private int trdPrizeCnt;

    public void addTotalScore(int score) {
        totalScore +=score;
    }

    public void addFstPrizeCnt(int cnt) {
        fstPrizeCnt +=cnt;
    }
    public void addSndPrizeCnt(int cnt) {
        sndPrizeCnt +=cnt;
    }
    public void addTrdPrizeCnt(int cnt) {
        trdPrizeCnt +=cnt;
    }

    @Override
    public int compareTo(OrgPrizeData o) {
        return o.totalScore - this.totalScore;
    }
}
