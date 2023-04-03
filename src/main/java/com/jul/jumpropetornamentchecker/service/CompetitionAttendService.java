package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.csvParser.CsvParser;
import com.jul.jumpropetornamentchecker.csvParser.FormCreator;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

//    public Boolean savePlayerByCsv(MultipartFile multipartFile, Long organizationId) {
//        boolean saveResult = true;
//
//        try {
//            Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new IllegalArgumentException());
//            csvParser.insertData(multipartFile, organization);
//
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//            saveResult = false;
//        } finally {
//            return saveResult;
//        }
//    }

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
