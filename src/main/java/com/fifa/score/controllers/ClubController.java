package com.fifa.score.controllers;

import com.fifa.score.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score/v1/clubController")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationClubFromLeague() {
        return clubService.initialisationClubFromLeague();
    }
}
