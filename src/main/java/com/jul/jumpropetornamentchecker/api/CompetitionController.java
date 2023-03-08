package com.jul.jumpropetornamentchecker.api;

import com.jul.jumpropetornamentchecker.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/competition")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

}
