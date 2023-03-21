package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.domain.Player;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.jul.jumpropetornamentchecker.dto.player.PlayerResponseDto;
import com.jul.jumpropetornamentchecker.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.jul.jumpropetornamentchecker.api.PlayerController.PlayerResponseEntityCreator.*;


@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;


    @PostMapping("/add")
    @Operation(summary = "선수 등록 API", description = "선수 정보를 입력하여 선수를 등록합니다.")
    public ResponseEntity<?> registerPlayer(@RequestBody PlayerRequestDto playerRequestDto) {
        Boolean saveResult = playerService.savePlayer(playerRequestDto);

        return getSavePlayerResponseEntity(saveResult);
    }

    @GetMapping("/find/all")
    @Operation(summary = "선수 전체 조회 API", description = "모든 선수 데이터를 불러옵니다.")
    public ResponseEntity<?> findAllPlayerData() {
        List<PlayerResponseDto> playerDatum = playerService.findAllPlayer();

        return getFindPlayerResponseEntity(playerDatum);
    }

    @GetMapping("/find")
    @Operation(summary = "선수 이름 조회 API", description = "선수 이름을 통헤 선수 정보를 조회합니다")
    public ResponseEntity<?> findPlayerDataByName(@RequestParam("name") String name) {
        List<PlayerResponseDto> playerDatum = playerService.findPlayerByName(name);

        return getFindPlayerResponseEntity(playerDatum);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "선수 ID 조회 API", description = "선수 ID를 통해 선수 정보를 조회합니다")
    public ResponseEntity<?> findPlayerDataById(@PathVariable Long id) {
        Optional<Player> playerData = playerService.findPlayerById(id);

        return getFindByIdPlayerResponseEntity(playerData);
    }


    protected static class PlayerResponseEntityCreator {
        static ResponseEntity<?> getFindPlayerResponseEntity(List<PlayerResponseDto> playerDatum) {
            return (playerDatum.isEmpty()) ?
                    new ResponseEntity<>("선수 정보를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(playerDatum, HttpStatus.OK);
        }

        static ResponseEntity<?> getFindByIdPlayerResponseEntity(Optional<Player> playerData) {
            return (playerData.isEmpty()) ?
                    new ResponseEntity<>("선수 정보를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(playerData.stream().map(Player::toDto), HttpStatus.OK);
        }

        static ResponseEntity<?> getSavePlayerResponseEntity(boolean saveResult) {
            return (saveResult) ?
                    new ResponseEntity<>("선수가 등록되었습니다.", HttpStatus.OK) :
                    new ResponseEntity<>("선수 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
