package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.domain.attend.Gender;
import com.jul.jumpropetornamentchecker.domain.attend.NumberTag;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendPlayerResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendUpdateDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendPlayerResponseDto;
import com.jul.jumpropetornamentchecker.dto.attend.eventAttend.EventAttendResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.excel.FormCreator;
import com.jul.jumpropetornamentchecker.excel.FormParser;
import com.jul.jumpropetornamentchecker.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionAttendService {

    private final CompetitionAttendRepository cmptAttendRepository;
    private final OrganizationRepository organizationRepository;
    private final CompetitionRepository competitionRepository;
    private final DepartmentRepository departmentRepository;
    private final EventAttendService eventAttendService;
    private final CompetitionEventRepository cmptEventRepository;
    private final EventAttendRepository eventAttendRepository;
    private final FormParser formParser;
    private final FormCreator formCreator;


    @Transactional
    public Boolean saveSinglePlayer(CompetitionAttendRequestDto cmptAttendRequestDto) {
        boolean saveResult = true;

        try {
            Competition competition = competitionRepository.findByCompetitionId(cmptAttendRequestDto.getCmptId()).orElseThrow(() -> new IllegalArgumentException("잘못되었거나 존재하지 않는 대회 번호입니다."));
            Department department = departmentRepository.findById(cmptAttendRequestDto.getDepartId()).orElseThrow(() -> new IllegalArgumentException("잘못되었거나 존재하지 않는 참가부 번호입니다."));
            Organization organization = organizationRepository.findById(cmptAttendRequestDto.getOrgId()).orElseThrow(() -> new IllegalArgumentException("잘못되었거나 존재하지 않는 기관 번호입니다."));

            //선수 소속이 입력되지 않으면 선수의 참가 기관명 입력
            String playerAffilication = cmptAttendRequestDto.getPlayerAffiliation();
            if (playerAffilication.isBlank()) {
                cmptAttendRequestDto.setPlayerAffiliation(organization.getOrgName());
            }

            CompetitionAttend competitionAttend = CompetitionAttend.from(competition, department, organization, cmptAttendRequestDto);
            CompetitionAttend savedCmptAttend = cmptAttendRepository.save(competitionAttend);

            eventAttendService.saveEventAttendData(competition, savedCmptAttend, cmptAttendRequestDto);

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
            createFromResult = formCreator.createForm(response, cmptId, orgId);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            return createFromResult;
        }

    }

    public List<CompetitionAttendPlayerResponseDto> findPlayersByOrgIdAndCmptId(Long orgId, Long cmptId) {

        List<CompetitionAttendPlayerResponseDto> cmptAttendPlayerDatum = new ArrayList<>();

        try {

            Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 기관ID입니다."));
            Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

            List<CompetitionAttend> cmptAttendDatum = cmptAttendRepository.findByOrganizationAndCompetition(organization, competition);

            for (CompetitionAttend competitionAttend : cmptAttendDatum) {
                for (EventAttendResponseDto eventAttendResponseDto : eventAttendService.findEventAttendByCmptAttend(competitionAttend)) {
                    CompetitionAttendPlayerResponseDto data = CompetitionAttendPlayerResponseDto.from(competitionAttend.toDto(), eventAttendResponseDto);
                    cmptAttendPlayerDatum.add(data);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return cmptAttendPlayerDatum;
    }


    public List<CompetitionAttendPlayerResponseDto> findPlayersByCmptId(Long cmptId) {

        List<CompetitionAttendPlayerResponseDto> cmptAttendPlayerDatum = new ArrayList<>();

        try {
            Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

            List<CompetitionAttend> cmptAttendDatum = cmptAttendRepository.findByCompetition(competition);

            for (CompetitionAttend competitionAttend : cmptAttendDatum) {
                for (EventAttendResponseDto eventAttendResponseDto : eventAttendService.findEventAttendByCmptAttend(competitionAttend)) {
                    CompetitionAttendPlayerResponseDto data = CompetitionAttendPlayerResponseDto.from(competitionAttend.toDto(), eventAttendResponseDto);
                    cmptAttendPlayerDatum.add(data);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return cmptAttendPlayerDatum;
    }

    public List<EventAttendPlayerResponseDto> findEventAttendPlayerDataByCmptAttendId(String cmptAttendId) {

        List<EventAttendPlayerResponseDto> eventAttendPlayerDatum = new ArrayList<>();

        try {
            CompetitionAttend competitionAttend = cmptAttendRepository.findById(cmptAttendId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회참가ID입니다."));
            for (EventAttendResponseDto eventAttendResponseDto : eventAttendService.findEventAttendByCmptAttend(competitionAttend)) {
                EventAttendPlayerResponseDto data = EventAttendPlayerResponseDto.from(competitionAttend.toDto(), eventAttendResponseDto);
                eventAttendPlayerDatum.add(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return eventAttendPlayerDatum;
    }

    public List<EventAttendPlayerResponseDto> findCmptEventAttendPlayersByCmptEventId(Long cmptEventId) {

        List<EventAttendPlayerResponseDto> eventAttendPlayerDatum = new ArrayList<>();

        try {
            CompetitionEvent competitionEvent = cmptEventRepository.findById(cmptEventId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회종목ID입니다."));
            List<EventAttend> eventAttendDatum = eventAttendRepository.findByCompetitionEvent(competitionEvent);

            for (EventAttend eventAttend : eventAttendDatum) {
                EventAttendPlayerResponseDto data = EventAttendPlayerResponseDto.from(eventAttend.getCompetitionAttend().toDto(), eventAttend.toDto());
                eventAttendPlayerDatum.add(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return eventAttendPlayerDatum;

    }

    public List<OrganizationResponseDto> findOrganizationsByCmptId(Long cmptId) {

        List<OrganizationResponseDto> organizationDatum = new ArrayList<>();

        try {
            Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

            List<CompetitionAttend> competitionAttendDatum = cmptAttendRepository.findByCompetition(competition);
            Set<OrganizationResponseDto> competitionResponseDtoSet = new HashSet<>();

            competitionAttendDatum.forEach(data -> competitionResponseDtoSet.add(data.getOrganization().toDto()));
            organizationDatum = new ArrayList<>(competitionResponseDtoSet);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return organizationDatum;

    }

    @Transactional
    public Boolean removePlayerByCmptAttendId(String cmptAttendId) {

        boolean removeResult = true;

        try {
            CompetitionAttend competitionAttend = cmptAttendRepository.findById(cmptAttendId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회참가ID입니다."));
            eventAttendRepository.deleteByCompetitionAttend(competitionAttend);
            cmptAttendRepository.delete(competitionAttend);
        } catch (Exception e) {
            log.error(e.getMessage());
            removeResult = false;
        } finally {
            return removeResult;
        }
    }

    @Transactional
    public Boolean removePlayerByCmptIdAndOrgId(Long cmptId, Long orgId) {

        boolean removeResult = true;

        try {

            Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 기관ID입니다."));
            Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

            List<CompetitionAttend> cmptAttendDatum = cmptAttendRepository.findByOrganizationAndCompetition(organization, competition);


            cmptAttendDatum.forEach(cmptAttendData -> {
                eventAttendRepository.deleteByCompetitionAttend(cmptAttendData);
                cmptAttendRepository.delete(cmptAttendData);
            });

        } catch (Exception e) {
            log.error(e.getMessage());
            removeResult = false;
        } finally {
            return removeResult;
        }
    }

    public List<NumberTag> makeNumberTagOnCompetition(Long cmptId) {
        List<NumberTag> numberTagDatum = new ArrayList<>();

        Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));
        List<CompetitionAttend> cmptAttendDatum = cmptAttendRepository.findByCompetition(competition);

        cmptAttendDatum.forEach(cmptAttendData -> {
            String[] splitedCmptAttendId = cmptAttendData.getCmptAttendId().split("-");
            String seperatedCmptAttendId = String.join("-", splitedCmptAttendId[1], splitedCmptAttendId[2]);

            numberTagDatum.add(NumberTag.builder()
                    .seperatedCmptAttendId(seperatedCmptAttendId)
                    .playerGender(cmptAttendData.getPlayerGender().getType())
                    .playerName(cmptAttendData.getPlayerName())
                    .departName(cmptAttendData.getDepartment().getDepartName())
                    .orgName(cmptAttendData.getOrganization().getOrgName())
                    .build());
        });

        return numberTagDatum;
    }

    public boolean updatePlayer(String cmptAttendId, CompetitionAttendUpdateDto updateDto) {
        boolean updateResult = true;

        try {
            Department department = departmentRepository.findById(updateDto.getDepartId()).orElseThrow();
            Gender gender = Gender.findByType(updateDto.getPlayerGender());
            CompetitionAttend competitionAttend = cmptAttendRepository.findById(cmptAttendId).orElseThrow();
            Competition competition = competitionAttend.getCompetition();

            List<EventAttend> beforeEventAttends = eventAttendRepository.findByCompetitionAttend(competitionAttend);
            List<Long> newCmptEventIds = updateDto.getCmptEventIds();


            for (EventAttend eventAttend : beforeEventAttends) {
                // 새로운 대회종목 데이터에 기존 종목 데이터가 포함되어 있으면 새로운 대회종목 데이터에서 빼기
                if(newCmptEventIds.contains(eventAttend.getCompetitionEvent().getCmptEventId())){
                    newCmptEventIds.remove(eventAttend.getCompetitionEvent().getCmptEventId());
                    continue;
                }
                // 이전 대회 종목 중, 새로운 대회 종목에 포함되어있지 않은 데이터 삭제
                eventAttendRepository.delete(eventAttend);
            }

            // 새로운 대회 종목 추가
            for (Long newCmptEventId : newCmptEventIds) {
                EventAttend eventAttendData = EventAttend.builder()
                        .competitionAttend(competitionAttend)
                        .competitionEvent(cmptEventRepository.findById(newCmptEventId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회종목ID입니다.")))
                        .competition(competition)
                        .score(0)
                        .grade(0)
                        .isPrinted(false)
                        .build();

                eventAttendRepository.save(eventAttendData);
            }


            cmptAttendRepository.updateByCmptAttendIdAndUpdateDto(
                    cmptAttendId,
                    department,
                    gender,
                    updateDto.getPlayerName(),
                    updateDto.getPlayerTel(),
                    updateDto.getPlayerBirth(),
                    updateDto.getPlayerAffiliation());

        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;

        } finally {
            return updateResult;
        }
    }

    public Optional<CompetitionAttend> findSinglePlayerByCmptAttendId(String cmptAttendId) {

        Optional<CompetitionAttend> cmptAttendPlayerData = Optional.empty();

        try {
            CompetitionAttend competitionAttend = cmptAttendRepository.findById(cmptAttendId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회참가ID입니다."));
            cmptAttendPlayerData = Optional.ofNullable(competitionAttend);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return cmptAttendPlayerData;

    }
}