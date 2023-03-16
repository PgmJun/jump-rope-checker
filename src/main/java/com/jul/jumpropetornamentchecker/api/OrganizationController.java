package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/add")
    @Operation(summary = "단체 추가 API", description = "단체 정보를 입력하여 단체를 추가합니다.")
    public ResponseEntity<?> registerOrganizationData(@RequestBody OrganizationRequestDto requestDto) {
        Boolean isSaved = organizationService.saveOrganization(requestDto);

        return (isSaved) ?
                new ResponseEntity<>("단체 등록에 성공하였습니다.", HttpStatus.OK) :
                new ResponseEntity<>("단체 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/all")
    @Operation(summary = "단체 전체 조회 API", description = "모든 단체 정보를 조회합니다.")
    public ResponseEntity<?> findAllOrganizationDatum() {
        List<OrganizationResponseDto> organizationDatum = organizationService.findAllOrganizationDatum();

        return (organizationDatum.isEmpty()) ?
                new ResponseEntity<>("단체 정보를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(organizationDatum, HttpStatus.OK);
    }

    @GetMapping("/find")
    @Operation(summary = "단체 이름 조회 API", description = "이름을 통해 단체 정보를 조회합니다.")
    public ResponseEntity<?> findOrganizationDatumByName(@RequestParam(name = "name") String orgName) {
        List<OrganizationResponseDto> organizationDatum = organizationService.findOrganizationByName(orgName);

        return (organizationDatum.isEmpty()) ?
                new ResponseEntity<>("단체 정보를 조회하지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(organizationDatum, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "단체 정보 삭제 API", description = "단체의 Id를 통해 단체 정보를 삭제합니다.")
    public ResponseEntity<?> deleteOrganizationDatumById(@RequestBody List<Long> organizationIds) {
        return organizationService.removeOrganizationData(organizationIds) ?
                new ResponseEntity<>("단체 정보가 삭제되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("단체 정보 삭제에 실패하였습니다.", HttpStatus.BAD_REQUEST);

    }



}
