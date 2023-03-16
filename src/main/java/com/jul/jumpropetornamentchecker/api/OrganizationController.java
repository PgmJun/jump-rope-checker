package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
