package com.jul.jumpropetornamentchecker.domain.attend;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NumberTag {
    private String seperatedCmptAttendId; //대회번호 분리된 배번
    private String playerName; //선수명
    private String departName; //부서명
    private String orgName; //기관명
    private String playerGender; //선수 성별
}
