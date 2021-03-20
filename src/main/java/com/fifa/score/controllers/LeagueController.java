package com.fifa.score.controllers;

import com.fifa.score.services.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score/v1/leagueController")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationCompetition() {
        return leagueService.initialisationOfCompetition();
    }

}

