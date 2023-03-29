package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationUpdateDto;
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

    public Optional<Organization> findOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    public Boolean removeOrganizationData(List<Long> organizationIds) {
        Boolean deleteResult = true;
        try {
            organizationIds.forEach(id -> organizationRepository.deleteByOrgId(id));
        } catch (Exception e) {
            log.error(e.getMessage());
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
