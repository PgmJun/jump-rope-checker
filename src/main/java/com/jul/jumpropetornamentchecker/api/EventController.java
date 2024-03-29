package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.dto.event.EventResponseDto;
import com.jul.jumpropetornamentchecker.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/event", produces = "application/json; charset=UTF8")
public class EventController extends ResponseEntityCreator {

    private final EventService eventService;

    @PutMapping("/renew")
    @Operation(summary = "종목 갱신 API", description = "종목 데이터를 새로 가져와 DB를 갱신합니다.")
    public ResponseEntity<?> renewEventData() {
        boolean saveResult = eventService.resetAndSaveEventData();
        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping("find/{id}")
    @Operation(summary = "종목 ID 조회 API", description = "ID를 통해 종목 정보를 조회합니다.")
    public ResponseEntity<?> findEventDataById(@PathVariable Long id) {
        Optional<Event> eventData = eventService.findEventDataById(id);
        return getFindDataResponseEntity(eventData);
    }

    @GetMapping("find/all")
    @Operation(summary = "종목 전체 조회 API", description = "모든 단체 정보를 조회합니다.")
    public ResponseEntity<?> findAllEventData() {
        List<EventResponseDto> eventDatum = eventService.findAllEventData();
        return getFindDatumResponseEntity(eventDatum);
    }

    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> data) {
        return (data.isEmpty()) ?
                new ResponseEntity<>("데이터를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(((Event) data.get()).toDto(), HttpStatus.OK);
    }
}
