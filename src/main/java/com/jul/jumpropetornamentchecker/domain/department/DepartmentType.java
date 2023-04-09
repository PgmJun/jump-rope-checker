package com.jul.jumpropetornamentchecker.domain.department;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum DepartmentType {
    KINDER(0L, "유치부"),
    ELEMENTARY1(1L, "초등1"),
    ELEMENTARY2(2L, "초등2"),
    ELEMENTARY3(3L, "초등3"),
    ELEMENTARY4(4L, "초등4"),
    ELEMENTARY5(5L, "초등5"),
    ELEMENTARY6(6L, "초등6"),
    MIDDLE(7L, "중고등부"),
    HIGH(8L, "일반부");


    private Long departId;
    private String departName;

    DepartmentType(Long departId, String departName) {
        this.departId = departId;
        this.departName = departName;
    }

    public static Department findDepartmentByName(String departName) {
        return Arrays.stream(values())
                .filter(d -> d.departName.equals(departName))
                .findAny()
                .map(Department::from)
                .orElseThrow(() -> new IllegalArgumentException("departName의 값이 존재하지 않거나 잘못 입력되었습니다."));
    }

    public static Department findDepartmentById(Long departId) {
        return Arrays.stream(values())
                .filter(d -> d.departId.equals(departId))
                .findAny()
                .map(Department::from)
                .orElseThrow(() -> new IllegalArgumentException("departId의 값이 존재하지 않거나 잘못 입력되었습니다."));
    }

    public static List<Department> getAllDepartmentData() {
        return Arrays.stream(values())
                .map(Department::from)
                .collect(Collectors.toList());
    }
}
