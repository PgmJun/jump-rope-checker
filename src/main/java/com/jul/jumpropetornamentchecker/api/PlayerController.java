package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerResponseDto;
import com.jul.jumpropetornamentchecker.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;


    @PostMapping("/add")
    @Operation(summary = "선수 등록 API", description = "선수 정보를 입력하여 선수를 등록합니다.")
    public ResponseEntity<?> registerPlayer(@RequestBody PlayerRequestDto playerRequestDto) {
        Boolean saveResult = playerService.savePlayer(playerRequestDto);

        return (saveResult) ?
                new ResponseEntity<>("선수가 등록되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("선수 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/all")
    @Operation(summary = "선수 전체 조회 API", description = "모든 선수 데이터를 불러온다")
    public ResponseEntity<?> findAllPlayerData() {
        List<PlayerResponseDto> playerDatum = playerService.findAllPlayer();

        return (playerDatum.isEmpty()) ?
                new ResponseEntity<>("선수 정보를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(playerDatum, HttpStatus.OK);
    }
}
