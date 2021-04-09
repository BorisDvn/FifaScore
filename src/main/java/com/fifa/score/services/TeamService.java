package com.fifa.score.services;

import com.fifa.score.models.Team;
import com.fifa.score.repositories.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;


    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(Team team) {
        teamRepository.save(team);
    }
}
