package com.jul.jumpropetornamentchecker.service;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.attend.EventAttend;
import com.jul.jumpropetornamentchecker.dto.prize.PrizeResponseDto;
import com.jul.jumpropetornamentchecker.repository.CompetitionRepository;
import com.jul.jumpropetornamentchecker.repository.EventAttendRepository;
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


    public List<PrizeResponseDto> getCompetitionPrizeData(Long cmptId) {
        List<PrizeResponseDto> prizeResponseDatum = new ArrayList<>();
        Competition competition = competitionRepository.findByCompetitionId(cmptId).orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."));

        //cmptId로 eventAttend 테이블 조회
        List<EventAttend> eventAttendDatum = eventAttendRepository.findByCompetition(competition);
        //eventAttend의 대회종목번호를 통해 대회 금은동, 123등 상 점수 조회하여 순위에 들었다면
        eventAttendDatum.forEach(eventAttendData -> {
            String playerName = eventAttendData.getCompetitionAttend().getPlayerName();
            String eventName = eventAttendData.getCompetitionEvent().getEvent().getEventName();

            // 단체전이면 단체명을 소속에 입력, 아니라면 소속명을 소속에 입력
            String affiliation = getPlayerAffiliation(eventAttendData);

            switch (eventAttendData.getGrade()) {
                case FIRST_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, FIRST_PRIZE_GRADE_NAME));
                    break;
                case SECOND_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, SECOND_PRIZE_GRADE_NAME));
                    break;
                case THIRD_PRIZE_GRADE:
                    prizeResponseDatum.add(makePrizeData(playerName, affiliation, eventName, THIRD_PRIZE_GRADE_NAME));
                    break;
                default:
                    log.info(eventAttendData.getCompetitionAttend().getCmptAttendId() + "대회참가번호 / " + eventAttendData.getCompetitionEvent().getCmptEventId() + "번 대회종목 /" +eventName + " /" + playerName + " 순위권에 해당하지 않는 데이터입니다.");
                    break;
            }
        });

        //PrizeResponseDto에 데이터를 담아 전송
        return prizeResponseDatum;
    }

    private String getPlayerAffiliation(EventAttend eventAttendData) {
        String affiliation;
        if(eventAttendData.getCompetitionEvent().getEvent().getIsGroupEvent()) {
            affiliation = eventAttendData.getCompetitionAttend().getOrganization().getOrgName();
        } else {
            affiliation = eventAttendData.getCompetitionAttend().getPlayerAffiliation();
        }
        return affiliation;
    }

    private PrizeResponseDto makePrizeData(String playerName, String playerAffiliation, String eventName, String prizeGradeName) {
        return PrizeResponseDto.builder()
                .playerName(playerName)
                .cmptName(eventName)
                .playerAffiliation(playerAffiliation)
                .grade(prizeGradeName)
                .build();
    }

}
