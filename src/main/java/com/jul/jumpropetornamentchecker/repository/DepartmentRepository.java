package com.jul.jumpropetornamentchecker.repository;

import com.jul.jumpropetornamentchecker.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
