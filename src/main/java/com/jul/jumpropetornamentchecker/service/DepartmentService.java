package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.domain.department.DepartmentType;
import com.jul.jumpropetornamentchecker.dto.attend.department.DepartmentResponseDto;
import com.jul.jumpropetornamentchecker.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jul.jumpropetornamentchecker.domain.department.DepartmentType.getAllDepartmentData;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public boolean renewDepartmentData() {
        boolean saveResult = true;
        try {
            departmentRepository.deleteAll();
            departmentRepository.saveAll(getAllDepartmentData());//체크
        } catch (Exception e) {
            log.error(e.getMessage());
            saveResult = false;
        } finally {
            return saveResult;
        }
    }

    public List<DepartmentResponseDto> findAllDepartmentData() {
        List<Department> departmentDatum = departmentRepository.findAll();

        return departmentDatum.stream()
                .map(Department::toDto)
                .collect(Collectors.toList());
    }

    public Department findByDepartmentId(Long departmentId) {
        return DepartmentType.findDepartmentById(departmentId);
    }
}
