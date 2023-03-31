package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.attend.department.DepartmentResponseDto;
import com.jul.jumpropetornamentchecker.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/depart")
public class DepartmentController extends ResponseEntityCreator {
    private final DepartmentService departmentService;

    @PutMapping("/renew")
    @Operation(summary = "부서 갱신 API", description = "부서 데이터를 새로 가져와 DB를 갱신합니다.")
    public ResponseEntity<?> renewDepartmentData() {
        boolean saveResult = departmentService.renewDepartmentData();

        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping("/find/all")
    @Operation(summary = "부서 전체 조회 API", description = "모든 부서 정보를 조회합니다.")
    public ResponseEntity<?> findAllDepartmentData() {
        List<DepartmentResponseDto> departmentDatum = departmentService.findAllDepartmentData();

        return getFindDatumResponseEntity(departmentDatum);
    }

    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData) {
        return null;
    }
}
