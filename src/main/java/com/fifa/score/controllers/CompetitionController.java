package com.fifa.score.controllers;

import com.fifa.score.models.Competition;
import com.fifa.score.services.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score/v1/competition")
public class CompetitionController {
    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationCompetition() {
        return competitionService.initialisationOfCompetition();
    }

    @GetMapping("competitions")
    public List<Competition> getAllCompetition() {
        return competitionService.findAllCompetition();
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") long id,
                                         @RequestBody Map<String, Object> competition) {
        return competitionService.updateCompetition(id, competition);
    }

}

