package com.fifa.score.services;

import com.fifa.score.models.League;
import com.fifa.score.repositories.LeagueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public ResponseEntity<String> addLeague(League league) {
        try {
            leagueRepository.save(league);
            return ResponseEntity.ok().body("Successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error: Can't add this league");
        }
    }

    public ResponseEntity<String> addLeague(List<League> leagues) {
        try {
            leagueRepository.saveAll(leagues);
            return ResponseEntity.ok().body("Successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error: Can't add this club");
        }
    }

}
