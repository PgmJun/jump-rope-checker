package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.dto.prize.PrizeResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrizeService {
    private final EventAttendRepository eventAttendRepository;
    private final CompetitionRepository competitionRepository;

    public static final int FIRST_PRIZE_GRADE = 3;
    public static final String FIRST_PRIZE_GRADE_NAME = "1위";
    public static final int SECOND_PRIZE_GRADE = 2;
    public static final String SECOND_PRIZE_GRADE_NAME = "2위";
    public static final int THIRD_PRIZE_GRADE = 1;
    public static final String THIRD_PRIZE_GRADE_NAME = "3위";


    @Transactional
    public List<PrizeResponseDto> getCompetitionPrizeData(Long cmptId) {
        List<PrizeResponseDto> prizeResponseDatum = new ArrayList<>();
        Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

        //cmptId로 eventAttend 테이블 조회
        List<EventAttend> eventAttendDatum = eventAttendRepository.findByCompetition(competition);

        //isPrinted TRUE로 변경
        changePrintStates(eventAttendDatum);

        //eventAttend의 대회종목번호를 통해 대회 금은동, 123등 상 점수 조회하여 순위에 들었다면
        eventAttendDatum.forEach(eventAttendData -> {
            String playerName = eventAttendData.getCompetitionAttend().getPlayerName();
            String eventName = eventAttendData.getCompetitionEvent().getEvent().getEventName();
            boolean isPrinted = eventAttendData.isPrinted();

            // 단체전이면 단체명을 소속에 입력, 아니라면 소속명을 소속에 입력
            String affiliation = getPlayerAffiliation(eventAttendData);

            switch (eventAttendData.getGrade()) {
                case FIRST_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, FIRST_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case SECOND_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, SECOND_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case THIRD_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, THIRD_PRIZE_GRADE_NAME, isPrinted));
                    break;
                default:
                    log.info(eventAttendData.getCompetitionAttend().getCmptAttendId() + "대회참가번호 / " + eventAttendData.getCompetitionEvent().getCmptEventId() + "번 대회종목 /" + eventName + " /" + playerName + " 순위권에 해당하지 않는 데이터입니다.");
                    break;
            }
        });

        //PrizeResponseDto에 데이터를 담아 전송
        return prizeResponseDatum;
    }

    @Transactional
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

            //isPrinted TRUE로 변경
            changePrintState(eventAttendData);

            String playerName = eventAttendData.getCompetitionAttend().getPlayerName();
            String eventName = eventAttendData.getCompetitionEvent().getEvent().getEventName();
            boolean isPrinted = eventAttendData.isPrinted();

            // 단체전이면 단체명을 소속에 입력, 아니라면 소속명을 소속에 입력
            String affiliation = getPlayerAffiliation(eventAttendData);

            switch (eventAttendData.getGrade()) {
                case FIRST_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, FIRST_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case SECOND_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, SECOND_PRIZE_GRADE_NAME, isPrinted));
                    break;
                case THIRD_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, THIRD_PRIZE_GRADE_NAME, isPrinted));
                    break;
                default:
                    log.info(eventAttendData.getCompetitionAttend().getCmptAttendId() + "대회참가번호 / " + eventAttendData.getCompetitionEvent().getCmptEventId() + "번 대회종목 /" + eventName + " /" + playerName + " 순위권에 해당하지 않는 데이터입니다.");
                    break;
            }
        }

        //PrizeResponseDto에 데이터를 담아 전송
        return prizeResponseDatum;
    }

    private void changePrintState(EventAttend eventAttendData) {
        eventAttendData.changePrintState();
    }

    private void changePrintStates(List<EventAttend> eventAttendDatum) {
        for (EventAttend eventAttendData : eventAttendDatum) {
            eventAttendData.changePrintState();
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

    private PrizeResponseDto makePrizeData(String playerName, String playerAffiliation, String eventName, String prizeGradeName, boolean isPrinted) {
        return PrizeResponseDto.builder()
                .playerName(playerName)
                .cmptName(eventName)
                .playerAffiliation(playerAffiliation)
                .grade(prizeGradeName)
                .isPrinted(isPrinted)
                .build();
    }

}
