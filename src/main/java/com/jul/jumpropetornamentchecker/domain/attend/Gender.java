package com.jul.jumpropetornamentchecker.domain.attend;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    MALE("남"), FEMALE("여");

    private final String type;

    Gender(String type) {
        this.type = type;
    }


    public static Gender findByType(String genderType) {
        return Arrays.stream(values())
                .filter(g -> g.type.equals(genderType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("genderType 변수의 값이 잘못 되었습니다. ['남', '여' 중 하나로 입력]"));
    }


}
