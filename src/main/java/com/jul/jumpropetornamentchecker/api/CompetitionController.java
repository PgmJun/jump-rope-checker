package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.dto.CompetitionRequestDto;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/competition")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @PostMapping("/add")
    public void registerCompetition(@RequestBody CompetitionRequestDto competitionDto) {
        competitionService.saveCompetition(competitionDto);
    }

}
