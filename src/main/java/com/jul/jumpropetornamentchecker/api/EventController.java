package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.dto.event.EventResponseDto;
import com.jul.jumpropetornamentchecker.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/event", produces = "application/json; charset=UTF8")
public class EventController extends ResponseEntityCreator {

    private final EventService eventService;

    @PostMapping("/save")
    @Operation(summary = "종목 리셋 및 재등록 API", description = "종목 데이터에서 종목을 끌어와 DB를 리셋 후 저장합니다.")
    public ResponseEntity<?> saveEventData() {
        boolean saveResult = eventService.resetAndSaveEventData();
        return getSaveDataResponseEntity(saveResult);
    }


    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> data) {
        return null;
    }
}
