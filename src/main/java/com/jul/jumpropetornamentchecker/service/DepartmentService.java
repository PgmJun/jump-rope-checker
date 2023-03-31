package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            getAllDepartmentData().forEach(department -> departmentRepository.save(department));
        } catch (Exception e) {
            log.error(e.getMessage());
            saveResult = false;
        } finally {
            return saveResult;
        }
    }
}
