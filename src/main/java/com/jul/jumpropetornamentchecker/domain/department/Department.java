package com.jul.jumpropetornamentchecker.domain.department;

import com.jul.jumpropetornamentchecker.dto.attend.department.DepartmentResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Department {

    @Id
    private Long departId;

    @Column
    private String departName;

    public static Department from(DepartmentType departmentType) {
        return Department.builder()
                .departId(departmentType.getDepartId())
                .departName(departmentType.getDepartName())
                .build();
    }

    public DepartmentResponseDto toDto() {
        return DepartmentResponseDto.builder()
                .departId(departId)
                .departName(departName)
                .build();
    }
}
