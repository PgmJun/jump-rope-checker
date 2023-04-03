package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.csvParser.CsvParser;
import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
import com.jul.jumpropetornamentchecker.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionAttendService {
    private final CompetitionAttendRepository cmptAttendRepository;
    private final OrganizationRepository organizationRepository;
    private final CompetitionRepository competitionRepository;
    private final DepartmentRepository departmentRepository;
    private final EventAttendService eventAttendService;
    private final CsvParser csvParser;


    @Transactional
    public Boolean saveSinglePlayer(CompetitionAttendRequestDto cmptAttendRequestDto) {
        boolean saveResult = true;

        try {
            Competition competition = competitionRepository.findByCompetitionId(cmptAttendRequestDto.getCmptId()).orElseThrow();
            Department department = departmentRepository.findById(cmptAttendRequestDto.getDepartId()).orElseThrow();
            Organization organization = organizationRepository.findById(cmptAttendRequestDto.getOrgId()).orElseThrow();

            CompetitionAttend competitionAttend = CompetitionAttend.from(competition, department, organization, cmptAttendRequestDto);
            CompetitionAttend savedCmptAttend = cmptAttendRepository.save(competitionAttend);

            eventAttendService.saveEventAttendData(savedCmptAttend, cmptAttendRequestDto);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            saveResult = false;
        } finally {
            return saveResult;
        }
    }

    public List<CompetitionAttendResponseDto> findPlayersByOrganizationId(Long orgId) {

        Organization organization = organizationRepository.findById(orgId).orElseThrow();
        List<CompetitionAttendResponseDto> cmptAttendDatum = cmptAttendRepository.findByOrganization(organization)
                .stream()
                .map(CompetitionAttend::toDto)
                .collect(Collectors.toList());

        return cmptAttendDatum;


    }

/*
    public Boolean savePlayerByCsv(MultipartFile multipartFile, Long organizationId) {
        boolean saveResult = true;

        try {
            Organization organization = organizationService.findOrganizationById(organizationId).orElseThrow(() -> new IllegalArgumentException());
            csvParser.insertData(multipartFile, organization);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            saveResult = false;
        } finally {
            return saveResult;
        }
    }

 */

}
