package com.fifa.score.services;

import com.fifa.score.models.Team;
import com.fifa.score.models.User;
import com.fifa.score.repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;


    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team findTeam(long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

    public void addTeam(Team team) {
        team.setCreation_date(LocalDate.now());
        teamRepository.save(team);
        // return ResponseEntity.ok().body("Successfully added");
    }

    public ResponseEntity<String> deleteTeam(long id) {
        Team team = findTeam(id);
        teamRepository.delete(team);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    public String addMember(User user, Team team) {
        // verify is member is already in Team
        if (!team.getMembers().contains(user)) {
            team.getMembers().add(user);
            teamRepository.save(team);
            return "Successfully added";
        } else {
            return null;
        }
    }

    public String addAdmin(User user, Team team) {
        // verify is member is already in Team
        if (team.getMembers().contains(user) && !team.getAdministrators().contains(user)) {
            team.getAdministrators().add(user);
            teamRepository.save(team);
            return "Successfully added";
        } else {
            return null;
        }
    }




}
