package com.fifa.score.services;

import com.fifa.score.models.Team;
import com.fifa.score.models.User;
import com.fifa.score.repositories.TeamRepository;
import com.fifa.score.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;


    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team findTeam(long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

    public Team addTeam(Team team) {
        team.setCreation_date(LocalDate.now());
        return teamRepository.save(team);
    }

    /*public ResponseEntity<String> deleteTeam(long id) {
        Team team = findTeam(id);
        teamRepository.delete(team);
        return ResponseEntity.ok().body("Successfully deleted");
    }*/

    public String addMember(User user, Team team) {
        // add standard c est pas mieux d envoyer les ids simplement?
        // verify is user is already in Team
        if (!user.getTeams().contains(team)) {
            user.getTeams().add(team);
            userRepository.save(user);
            return "Successfully added";
        } else {
            return "Member is already in Team";
        }
    }

    public String deleteMember(User user, Team team) {
        // add standard c est pas mieux d envoyer les ids simplement?
        // verify is member is already in Team
        if (user.getTeams().contains(team)) {
            user.getTeams().remove(team);
            userRepository.save(user);
            return "Successfully removed";
        } else {
            return "Member can not be removed";
        }
    }

    public String addAdmin(User user, Team team) {
        // add standard c est pas mieux d envoyer les ids simplement?
        // verify is member is already in Team
        if (!user.getTeams().contains(team)) {
            return "User is not member";
        } else if (!user.getAdmin_for_team().contains(team)) {
            user.getAdmin_for_team().add(team);
            userRepository.save(user);
            return "Successfully added";
        } else {
            return "Member is already admin";
        }
    }


    // remove admin recht
    public String deleteAdmin(User user, Team team) {
        // add standard c est pas mieux d envoyer les ids simplement?
        // verify is member is already in Team
        if (user.getAdmin_for_team().contains(team)) {
            user.getAdmin_for_team().remove(team);
            userRepository.save(user);
            return "Successfully removed";
        } else {
            return "Admin can not be removed";
        }
    }


}
