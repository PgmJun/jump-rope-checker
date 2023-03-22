package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.api.tools.ResponseEntityCreator;
import com.jul.jumpropetornamentchecker.domain.player.Player;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerResponseDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerUpdateDto;
import com.jul.jumpropetornamentchecker.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping(value = "/player", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
public class PlayerController extends ResponseEntityCreator {
    private final PlayerService playerService;


    @PostMapping("/add")
    @Operation(summary = "선수 등록 API", description = "선수 정보를 입력하여 선수를 등록합니다.")
    public ResponseEntity<?> registerPlayer(@RequestBody PlayerRequestDto playerRequestDto) {
        Boolean saveResult = playerService.savePlayer(playerRequestDto);

        return getSaveDataResponseEntity(saveResult);
    }

    @GetMapping("/find/all")
    @Operation(summary = "선수 전체 조회 API", description = "모든 선수 데이터를 불러옵니다.")
    public ResponseEntity<?> findAllPlayerData() {
        List<PlayerResponseDto> playerDatum = playerService.findAllPlayer();

        return getFindDatumResponseEntity(playerDatum);
    }

    @GetMapping("/find")
    @Operation(summary = "선수 이름 조회 API", description = "선수 이름을 통헤 선수 정보를 조회합니다")
    public ResponseEntity<?> findPlayerDataByName(@RequestParam("name") String name) {
        List<PlayerResponseDto> playerDatum = playerService.findPlayerByName(name);

        return getFindDatumResponseEntity(playerDatum);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "선수 ID 조회 API", description = "선수 ID를 통해 선수 정보를 조회합니다")
    public ResponseEntity<?> findPlayerDataById(@PathVariable Long id) {
        Optional<Player> playerData = playerService.findPlayerById(id);

        return getFindDataResponseEntity(playerData);
    }

    @GetMapping("/find/org/{organizationId}")
    @Operation(summary = "단체 소속 선수 조회 API", description = "단체 ID를 통해 단체에 소속된 선수 정보를 조회합니다")
    public ResponseEntity<?> findPlayerDatumByOrganizationId(@PathVariable Long organizationId) {
        List<PlayerResponseDto> playerDatum = playerService.findPlayerDataByOrganizationId(organizationId);

        return getFindDatumResponseEntity(playerDatum);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "선수 정보 삭제 API", description = "선수의 Id를 통해 선수 정보를 삭제합니다.")
    public ResponseEntity<?> deletePlayerDatumById(@RequestBody List<Long> playerIds) {
        boolean removeResult = playerService.removePlayerData(playerIds);

        return getRemoveDataResponseEntity(removeResult);
    }

    @PutMapping("/update")
    @Operation(summary = "선수 정보 수정 API", description = "선수 정보를 업데이트합니다.")
    public ResponseEntity<?> updatePlayerData(@RequestBody PlayerUpdateDto playerUpdateDto) {
        Boolean updateResult = playerService.updatePlayerData(playerUpdateDto);

        return getUpdateDataResponseEntity(updateResult);
    }

    @Override
    public ResponseEntity<?> getFindDataResponseEntity(Optional<?> playerData) {
        return (playerData.isEmpty()) ?
                new ResponseEntity<>("데이터를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(((Player)playerData.get()).toDto(), HttpStatus.OK);
    }

}
