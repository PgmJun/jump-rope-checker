package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationUpdateDto;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/organization", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
public class OrganizationController extends ResponseEntityCreator {
    private final OrganizationService organizationService;


    @PostMapping("/add")
    @Operation(summary = "단체 추가 API", description = "단체 정보를 입력하여 단체를 추가합니다.")
    public ResponseEntity<?> registerOrganizationData(@RequestBody OrganizationRequestDto requestDto) {
        Boolean saveResult = organizationService.saveOrganization(requestDto);

        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping("/find/all")
    @Operation(summary = "단체 전체 조회 API", description = "모든 단체 정보를 조회합니다.")
    public ResponseEntity<?> findAllOrganizationDatum() {
        List<OrganizationResponseDto> organizationDatum = organizationService.findAllOrganizationDatum();

        return getFindDatumResponseEntity(organizationDatum);
    }

    @GetMapping("/find")
    @Operation(summary = "단체 이름 조회 API", description = "이름을 통해 단체 정보를 조회합니다.")
    public ResponseEntity<?> findOrganizationDatumByName(@RequestParam(name = "name") String orgName) {
        List<OrganizationResponseDto> organizationDatum = organizationService.findOrganizationByName(orgName);

        return getFindDatumResponseEntity(organizationDatum);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "단체 ID 조회 API", description = "ID를 통해 단체 정보를 조회합니다.")
    public ResponseEntity<?> findOrganizationDataById(@PathVariable Long id) {
        Optional<OrganizationResponseDto> organizationData = organizationService.findOrganizationById(id);

        return getFindDataResponseEntity(organizationData);
    }

    @DeleteMapping("/delete/{orgId}")
    @Operation(summary = "단체 정보 삭제 API", description = "단체의 Id를 통해 단체 정보를 삭제합니다.")
    public ResponseEntity<?> deleteOrganizationDatumById(@PathVariable Long orgId) {
        Boolean removeResult = organizationService.removeOrganizationData(orgId);

        return getRemoveDataResponseEntity(removeResult);
    }

    @PatchMapping("/update")
    @Operation(summary = "단체 정보 수정 API", description = "단체 정보를 업데이트합니다.")
    public ResponseEntity<?> updateOrganizationData(@RequestBody OrganizationUpdateDto organizationUpdateDto) {
        Boolean updateResult = organizationService.updateOrganizationData(organizationUpdateDto);

        return getUpdateDataResponseEntity(updateResult);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> data) {
        return (data.isEmpty()) ?
                new ResponseEntity<>("데이터를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(((Organization) data.get()).toDto(), HttpStatus.OK);
    }


}
