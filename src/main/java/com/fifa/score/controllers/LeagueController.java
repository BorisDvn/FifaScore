package com.fifa.score.controllers;

import com.fifa.score.models.League;
import com.fifa.score.services.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score/v1/league")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationCompetition() {
        return leagueService.initialisationOfCompetition();
    }

    @GetMapping("")
    public List<League> getAllLeagues() {
        return leagueService.findAll();
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") long id,
                                         @RequestBody Map<String, Object> league){
        return leagueService.updateLeague(id, league);
    }

}

