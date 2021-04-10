package com.fifa.score.controllers;

import com.fifa.score.models.Team;
import com.fifa.score.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score/v1/team/")
public class TeamController {

    final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("teams")
    public List<Team> getAllTeam(){
        return teamService.findAllTeam();
    }

    /*@PostMapping("addTeam")
    public ResponseEntity<String> addTeam(@RequestBody Team team) {
        teamService.addTeam(team);
        return ResponseEntity.ok().body("Successfully added");
    }*/
}
