package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.OrgPrizeData;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.attend.CompetitionAttend;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.dto.prize.PrizeResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionAttendRepository;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
import com.jul.jumpropetornamentchecker.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrizeService {
    private final EventAttendRepository eventAttendRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitionAttendRepository cmptAttendRepository;
    private final CompetitionAttendService cmptAttendService;
    private final OrganizationRepository orgRepository;

    public static final int FIRST_PRIZE_GRADE = 3;
    public static final String FIRST_PRIZE_GRADE_NAME = "1위";
    public static final int SECOND_PRIZE_GRADE = 2;
    public static final String SECOND_PRIZE_GRADE_NAME = "2위";
    public static final int THIRD_PRIZE_GRADE = 1;
    public static final String THIRD_PRIZE_GRADE_NAME = "3위";


    public List<PrizeResponseDto> getCompetitionPrizeData(Long cmptId) {
        List<PrizeResponseDto> prizeResponseDatum = new ArrayList<>();
        Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

        //cmptId로 eventAttend 테이블 조회
        List<EventAttend> eventAttendDatum = eventAttendRepository.findByCompetition(competition);

        //eventAttend의 대회종목번호를 통해 대회 금은동, 123등 상 점수 조회하여 순위에 들었다면
        eventAttendDatum.forEach(eventAttendData -> {
            String playerName = eventAttendData.getCompetitionAttend().getPlayerName();
            String eventName = eventAttendData.getCompetitionEvent().getEvent().getEventName();
            Long eventAttendId = eventAttendData.getEventAttendId();
            boolean isPrinted = eventAttendData.isPrinted();

            // 단체전이면 단체명을 소속에 입력, 아니라면 소속명을 소속에 입력
            String affiliation = getPlayerAffiliation(eventAttendData);

            switch (eventAttendData.getGrade()) {
                case FIRST_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(eventAttendId, playerName, affiliation, eventName, FIRST_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case SECOND_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(eventAttendId, playerName, affiliation, eventName, SECOND_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case THIRD_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(eventAttendId, playerName, affiliation, eventName, THIRD_PRIZE_GRADE_NAME, isPrinted));
                    break;
                default:
                    log.info(eventAttendData.getCompetitionAttend().getCmptAttendId() + "대회참가번호 / " + eventAttendData.getCompetitionEvent().getCmptEventId() + "번 대회종목 /" + eventName + " /" + playerName + " 순위권에 해당하지 않는 데이터입니다.");
                    break;
            }
        });

        //PrizeResponseDto에 데이터를 담아 전송
        return prizeResponseDatum;
    }

    public List<PrizeResponseDto> getCompetitionPrizeDataByOrgId(Long cmptId, Long orgId) {
        List<PrizeResponseDto> prizeResponseDatum = new ArrayList<>();
        Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

        //cmptId로 eventAttend 테이블 조회
        List<EventAttend> eventAttendDatum = eventAttendRepository.findByCompetition(competition);


        //eventAttend의 대회종목번호를 통해 대회 금은동, 123등 상 점수 조회하여 순위에 들었다면
        for (EventAttend eventAttendData : eventAttendDatum) {

            // 단체 번호가 일치하는지 확인
            Long playerOrgId = eventAttendData.getCompetitionAttend().getOrganization().getOrgId();
            if (playerOrgId != orgId) {
                continue;
            }

            String playerName = eventAttendData.getCompetitionAttend().getPlayerName();
            String eventName = eventAttendData.getCompetitionEvent().getEvent().getEventName();
            boolean isPrinted = eventAttendData.isPrinted();
            Long eventAttendId = eventAttendData.getEventAttendId();

            // 단체전이면 단체명을 소속에 입력, 아니라면 소속명을 소속에 입력
            String affiliation = getPlayerAffiliation(eventAttendData);

            switch (eventAttendData.getGrade()) {
                case FIRST_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(eventAttendId, playerName, affiliation, eventName, FIRST_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case SECOND_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(eventAttendId, playerName, affiliation, eventName, SECOND_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case THIRD_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(eventAttendId, playerName, affiliation, eventName, THIRD_PRIZE_GRADE_NAME, isPrinted));
                    break;
                default:
                    log.info(eventAttendData.getCompetitionAttend().getCmptAttendId() + "대회참가번호 / " + eventAttendData.getCompetitionEvent().getCmptEventId() + "번 대회종목 /" + eventName + " /" + playerName + " 순위권에 해당하지 않는 데이터입니다.");
                    break;
            }
        }

        //PrizeResponseDto에 데이터를 담아 전송
        return prizeResponseDatum;
    }

    @Transactional
    public boolean changePrintStates(List<Long> eventAttendIds) {
        boolean updateResult = true;

        try {
            for (Long eventAttendId : eventAttendIds) {
                EventAttend eventAttend = eventAttendRepository.findById(eventAttendId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 종목참가ID 입니다."));
                eventAttend.changePrintState();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            updateResult = false;
        } finally {
            return updateResult;
        }
    }

    private String getPlayerAffiliation(EventAttend eventAttendData) {
        String affiliation;
        if (eventAttendData.getCompetitionEvent().getEvent().getIsGroupEvent()) {
            affiliation = eventAttendData.getCompetitionAttend().getOrganization().getOrgName();
        } else {
            affiliation = eventAttendData.getCompetitionAttend().getPlayerAffiliation();
        }
        return affiliation;
    }

    private PrizeResponseDto makePrizeData(Long eventAttendId, String playerName, String playerAffiliation, String eventName, String prizeGradeName, boolean isPrinted) {
        return PrizeResponseDto.builder()
                .eventAttendId(eventAttendId)
                .playerName(playerName)
                .cmptName(eventName)
                .playerAffiliation(playerAffiliation)
                .grade(prizeGradeName)
                .isPrinted(isPrinted)
                .build();
    }

    public List<OrgPrizeData> getOrganizationPrizeDataByCmptId(Long cmptId) {
        Competition competition = competitionRepository.findById(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));
        List<CompetitionAttend> cmptAttendDatum = cmptAttendRepository.findByCompetition(competition);
        List<OrganizationResponseDto> cmptAttendOrgDatum = cmptAttendService.findOrganizationsByCmptId(cmptId);


        Map<Long, OrgPrizeData> organizationTotalScore = new HashMap<>();

        // 대회 참가 단체 정보 입력
        for (OrganizationResponseDto cmptAttendOrg : cmptAttendOrgDatum) {
            Organization org = orgRepository.findById(cmptAttendOrg.orgId()).orElseThrow(() -> new IllegalArgumentException("잘못되었거나 존재하지 않는 단체ID입니다."));
            OrgPrizeData orgPrizeData = OrgPrizeData.builder()
                    .orgName(org.getOrgName())
                    .fstPrizeCnt(0)
                    .sndPrizeCnt(0)
                    .trdPrizeCnt(0)
                    .totalScore(0)
                    .build();

            organizationTotalScore.put(org.getOrgId(), orgPrizeData);
        }

        //점수 삽입
        eventScoreInsert(cmptAttendDatum, organizationTotalScore);

        //총 점수대로 데이터 정렬
        List<OrgPrizeData> sortedOrgPrizeData = sortOrgPrizeData(organizationTotalScore);
        return sortedOrgPrizeData;
    }

    private List<OrgPrizeData> sortOrgPrizeData(Map<Long, OrgPrizeData> organizationTotalScore) {
        List<Map.Entry<Long, OrgPrizeData>> entryList = new LinkedList<>(organizationTotalScore.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        List<OrgPrizeData> sortedOrgPrizeDatum = new LinkedList<>();
        for (Map.Entry<Long, OrgPrizeData> entry : entryList) {
            sortedOrgPrizeDatum.add(entry.getValue());
        }

        return sortedOrgPrizeDatum;
    }

    private void eventScoreInsert(List<CompetitionAttend> cmptAttendDatum, Map<Long, OrgPrizeData> organizationTotalScore) {
        for (CompetitionAttend cmptAttend : cmptAttendDatum) {
            Organization org = cmptAttend.getOrganization();
            //System.out.println("org.getOrgId(), org.getOrgName() = " + org.getOrgId() + ", " + org.getOrgName());

            List<EventAttend> eventAttends = eventAttendRepository.findByCompetitionAttend(cmptAttend);
            for (EventAttend eventAttend : eventAttends) {
                OrgPrizeData updatedOrgPrizeData = organizationTotalScore.get(org.getOrgId());

                int eventGradeScore = findCmptEventScore(eventAttend.getCompetitionEvent(), eventAttend.getGrade());
                //수상 카운트 추가
                updatedOrgPrizeData = addPrizeCnt(updatedOrgPrizeData, eventAttend.getGrade());
                //총 합산 점수 연산
                updatedOrgPrizeData.addTotalScore(eventGradeScore);
                //참가 점수 합산
                updatedOrgPrizeData.addTotalScore(eventAttend.getCompetitionEvent().getPartPoint());

                organizationTotalScore.put(org.getOrgId(), updatedOrgPrizeData);
            }
        }
    }

    private int findCmptEventScore(CompetitionEvent competitionEvent, int grade) {
        if (grade == 3) {
            return competitionEvent.getFstPrizeStandard();
        } else if (grade == 2) {
            return competitionEvent.getSndPrizeStandard();
        } else if (grade == 1) {
            return competitionEvent.getTrdPrizeStandard();
        } else {
            return 0;
        }
    }

    private OrgPrizeData addPrizeCnt(OrgPrizeData orgPrizeData, int grade) {
        if (grade == 3) {
            orgPrizeData.addFstPrizeCnt(1);
        } else if (grade == 2) {
            orgPrizeData.addSndPrizeCnt(1);
        } else if (grade == 1) {
            orgPrizeData.addTrdPrizeCnt(1);
        }

        return orgPrizeData;
    }
}
