package com.jul.jumpropetornamentchecker.domain.player;

import java.util.Arrays;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private String type;

    Gender(String type) {
        this.type = type;
    }


    public static Gender getGender(String genderType) {
        return Arrays.stream(values())
                .filter(g -> g.type.equals(genderType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("genderType 변수의 값이 잘못 되었습니다. [대소문자 구분없이 'male', 'female' 중 하나로 입력]"));
    }


}
