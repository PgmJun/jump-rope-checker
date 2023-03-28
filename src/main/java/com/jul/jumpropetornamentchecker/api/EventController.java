package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/event", produces = "application/json; charset=UTF8")
public class EventController extends ResponseEntityCreator {

    private final EventService eventService;

    @PostMapping("/save")
    @Operation(summary = "종목 리셋 및 재등 API", description = "종목 데이터에서 종목을 끌어와 DB를 리셋 후 저장합니다.")
    public ResponseEntity<?> saveEventData() {
        boolean saveResult = eventService.resetAndSaveEventData();
        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping("find/{id}")
    @Operation(summary = "종목 ID 조회 API", description = "ID를 통해 종목 정보를 조회합니다.")
    public ResponseEntity<?> findEventDataById(@PathVariable Long id) {
        Optional<Event> eventData = eventService.findEventDataById(id);
        return getFindDataResponseEntity(eventData);
    }

    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> data) {
        return (data.isEmpty()) ?
                new ResponseEntity<>("데이터를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(((Event) data.get()).toDto(), HttpStatus.OK);
    }
}
