package com.jul.jumpropetornamentchecker.domain.department;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum DepartmentType {
    KINDER(0L, "유치부"),
    ELEMENTARY1(1000L, "초등1"),
    ELEMENTARY2(2000L, "초등2"),
    ELEMENTARY3(3000L, "초등3"),
    ELEMENTARY4(4000L, "초등4"),
    ELEMENTARY5(5000L, "초등5"),
    ELEMENTARY6(6000L, "초등6"),
    MIDDLE(7000L, "중등부"),
    HIGH(8000L, "고등부"),
    GENERAL(9000L, "일반부");


    private Long departId;
    private String departName;

    DepartmentType(Long departId, String departName) {
        this.departId = departId;
        this.departName = departName;
    }

    public static DepartmentType findDepartmentTypeByName(String departName) {
        return Arrays.stream(values())
                .filter(d -> d.departName.equals(departName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("departName의 값이 존재하지 않거나 잘못 입력되었습니다."));
    }

    public static DepartmentType findDepartmentTypeById(Long departId) {
        return Arrays.stream(values())
                .filter(d -> d.departId.equals(departId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("departId의 값이 존재하지 않거나 잘못 입력되었습니다."));
    }

    public static List<Department> getAllDepartmentData() {
        return Arrays.stream(values())
                .map(Department::from)
                .collect(Collectors.toList());
    }
}