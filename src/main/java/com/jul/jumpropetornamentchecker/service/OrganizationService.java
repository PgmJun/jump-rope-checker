package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationUpdateDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionAttendRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
import com.jul.jumpropetornamentchecker.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final CompetitionAttendRepository cmptAttendRepository;
    private final EventAttendRepository eventAttendRepository;

    public Boolean saveOrganization(OrganizationRequestDto requestDto) {
        Boolean registerResult = true;

        try {
            organizationRepository.save(requestDto.to());
        } catch (Exception e) {
            log.error(e.getMessage());
            registerResult = false;
        } finally {
            return registerResult;
        }
    }

    public List<OrganizationResponseDto> findAllOrganizationDatum() {
        List<Organization> allOrganizationDatum = organizationRepository.findAll();

        return allOrganizationDatum.stream()
                .map(Organization::toDto)
                .collect(Collectors.toList());

    }

    public List<OrganizationResponseDto> findOrganizationByName(String orgName) {
        List<Organization> organizationDatum = organizationRepository.findByOrgName(orgName);

        return organizationDatum.stream()
                .map(Organization::toDto)
                .collect(Collectors.toList());
    }

    public Optional<OrganizationResponseDto> findOrganizationById(Long id) {
        Optional<Organization> organizationData = organizationRepository.findById(id);

        return organizationData
                .map(Organization::toDto);
    }

    @Transactional
    public Boolean removeOrganizationData(Long orgId) {
        Boolean deleteResult = true;
        try {
            Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 단체ID입니다."));

            //대회참가 CompetitionAttend 삭제
            //종목참가 EventAttend 삭제
            List<CompetitionAttend> cmptAttends = cmptAttendRepository.findByOrganization(organization);
            cmptAttends.forEach(c -> {
                eventAttendRepository.deleteByCompetitionAttend(c);
                cmptAttendRepository.delete(c);
            });

            //단체 Organization 삭제
            organizationRepository.deleteByOrgId(orgId);
        } catch (Exception e) {
            log.error(e.getMessage());
            deleteResult = false;
        } finally {
            return deleteResult;
        }
    }

    @Transactional
    public Boolean updateOrganizationData(OrganizationUpdateDto organizationUpdateDto) {
        Boolean updateResult = true;

        try {
            Organization organizationData = organizationRepository.findById(organizationUpdateDto.orgId()).orElseThrow();
            organizationData.changeData(organizationUpdateDto);

        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;

        } finally {
            return updateResult;
        }
    }
}
