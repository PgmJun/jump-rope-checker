package com.jul.jumpropetornamentchecker.dto.attend.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DepartmentResponseDto {

    private Long departId;
    private String departName;
}
