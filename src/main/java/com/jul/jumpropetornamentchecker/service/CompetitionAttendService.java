package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.excel.FormParser;
import com.jul.jumpropetornamentchecker.excel.FormCreator;
import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionAttendRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import com.jul.jumpropetornamentchecker.repository.DepartmentRepository;
import com.jul.jumpropetornamentchecker.repository.OrganizationRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final FormParser formParser;
    private final FormCreator formCreator;


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

    @Transactional
    public Boolean savePlayerByAttendForm(MultipartFile attendForm) {
        boolean saveResult = true;

        try {
            List<CompetitionAttendRequestDto> competitionAttendRequestDtos = formParser.parseFormData(attendForm);
            competitionAttendRequestDtos.forEach(dto -> saveSinglePlayer(dto));

        } catch (Exception e) {
            log.error(e.getMessage());
            saveResult = false;
        } finally {
            return saveResult;
        }
    }

    public boolean createCompetitionAttendForm(HttpServletResponse response, Long cmptId, Long orgId) {
        boolean createFromResult = true;
        try {
            formCreator.createForm(response, cmptId, orgId);

        } catch (Exception e) {
            log.error(e.getMessage());
            createFromResult = false;
        } finally {
            return createFromResult;
        }

    }

    public List<CompetitionAttendResponseDto> findPlayersByOrgIdAndCmptId(Long orgId, Long cmptId) {

        Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 기관ID입니다."));
        Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));
        List<CompetitionAttendResponseDto> cmptAttendDatum = cmptAttendRepository.findByOrganizationAndCompetition(organization, competition)
                .stream()
                .map(CompetitionAttend::toDto)
                .collect(Collectors.toList());

        return cmptAttendDatum;
    }

}
