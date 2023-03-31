package com.jul.jumpropetornamentchecker.domain.department;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table
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
}
