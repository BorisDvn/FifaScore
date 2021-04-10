package com.fifa.score.services;

import com.fifa.score.models.Team;
import com.fifa.score.repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class TeamService {

    private final TeamRepository teamRepository;


    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team findTeam (Long id){
        return teamRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> addTeam(Team team) {
        team.setCreation_date(LocalDate.now());
        teamRepository.save(team);
        return ResponseEntity.ok().body("Successfully added");
    }

    public ResponseEntity<String> deleteTeam(Long id) {
        Team team = findTeam(id);
        teamRepository.delete(team);
        return ResponseEntity.ok().body("Successfully deleted");
    }

}
