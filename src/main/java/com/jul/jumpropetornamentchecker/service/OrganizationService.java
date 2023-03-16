package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.dto.organization.OrganizationRequestDto;
import com.jul.jumpropetornamentchecker.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
}
