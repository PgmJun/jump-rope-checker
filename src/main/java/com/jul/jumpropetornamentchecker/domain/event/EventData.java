package com.jul.jumpropetornamentchecker.domain.event;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EventData {

    EVENT1(1L,"두발모아빨리뛰기", false),
    EVENT2(2L,"30초번갈아뛰기", false),
    EVENT3(3L,"2중뛰기", false),
    EVENT4(4L,"엇걸어풀어뛰기", false),
    EVENT5(5L,"3중뛰기", false),
    EVENT6(6L,"솔개뛰기", false),
    EVENT7(7L,"2인맞서서넘기", false),
    EVENT8(8L,"2인번갈아넘기", false),
    EVENT9(9L,"2인스피드릴레이", false),
    EVENT10(10L,"쌍줄스피드릴레이", true),
    EVENT11(11L,"스피드릴레이", true),
    EVENT12(12L,"왕중왕전", false),
    EVENT13(13L,"8자마라톤", true),
    EVENT14(14L,"긴줄손가위바위보", true),
    EVENT15(15L,"긴줄4도약", true),
    EVENT16(16L,"긴줄빨리넘기", true),
    EVENT17(17L,"긴줄뛰어들어함께뛰기", true),
    EVENT18(1000L,"개인프리스타일", false),
    EVENT19(2000L,"음악줄넘기", true);

    private Long eventId;
    private String eventName;
    private Boolean isGroupGame;


    EventData(Long eventId, String eventName, Boolean isGroupGame) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.isGroupGame = isGroupGame;
    }



    public static List<Event> getAllEventData() {
        return Arrays.stream(values())
                .map(Event::from)
                .collect(Collectors.toList());
    }
}
