package com.fifa.score.services;

import com.fifa.score.models.Team;
import com.fifa.score.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;


    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public ResponseEntity<String> addTeam(Team team) {
        teamRepository.save(team);
        return ResponseEntity.ok().body("Successfully added");
    }
}
