package com.fifa.score.services;

import com.fifa.score.models.Team;
import com.fifa.score.models.User;
import com.fifa.score.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TeamService teamService;

    public UserService(UserRepository userRepository, TeamService teamService) {
        this.userRepository = userRepository;
        this.teamService = teamService;
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> addUser(User user) {
        userRepository.save(user);
        return ResponseEntity.ok().body("Successfully added");
    }

    public ResponseEntity<String> deleteUser(long id) {
        User user = findUser(id);
        userRepository.delete(user);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    public ResponseEntity<String> createTeam(Team team) {
        // get the authenticated user
        // temporary use temp user :-)
        User user_temp = findUser(1L);
        team.setOwner(user_temp);
        // save new team
        Team team_toSave = teamService.addTeamReturnTeam(team);

        // update user _ members
       List<Team> teams = user_temp.getTeams();
       teams.add(team_toSave);
       user_temp.setTeams(teams);
       userRepository.save(user_temp);





        //team.setMembers(Collections.singletonList(user_temp));
        //team.setAdministrators(Collections.singletonList(user_temp));
        //System.out.println(team);


        // update user
        //System.out.println("w");
        //System.out.println(team_help);
        //user_temp.setAdmin_for_team(Collections.singletonList(team_help));
        //user_temp.setTeams(Collections.singletonList(team_help));
        //userRepository.save(user_temp);
        return ResponseEntity.ok().body("Successfully created");
    }

    public ResponseEntity<String> deleteTeam(long id_team) {
        /*
         verify if user is the owner // or may be also admin?
         use isAdminOrOwner
         get the authenticated user
        */
        Team team = teamService.findTeam(id_team);
        if (team.getOwner().getId_user() == 1L) { // temporary use temp user :-)
            return teamService.deleteTeam(id_team);
        } else {
            return ResponseEntity.ok().body("You are not the owner!");
        }
    }

    /*public ResponseEntity<String> addMemberInTeam(long id_user, long id_team) {
        // get the authenticated user and check if he is admin or owner

        // User to add
        User user = findUser(id_user);
        Team team = teamService.findTeam(id_team);

        // check with temp 1
        if (isAdminOrOwner(team)) {
            String added = teamService.addMember(user, team);
            if (added.equals("Successfully added")) {
                return ResponseEntity.ok().body("Member successfully added");
            } else {
                return ResponseEntity.ok().body("Member is member is already in Team");
            }
        } else {
            return ResponseEntity.ok().body("You are not admin");
        }
    }*/

    /*public ResponseEntity<String> addAdminInTeam(long id_user, long id_team) {
        // get the authenticated user and check if he is admin or owner

        // User to set admin
        User user = findUser(id_user);
        Team team = teamService.findTeam(id_team);

        // check with temp 1
        if (isAdminOrOwner(team)) {
            String added = teamService.addAdmin(user, team);
            if (added.equals("Successfully added")) {
                return ResponseEntity.ok().body("Member successfully added");
            } else {
                return ResponseEntity.ok().body("Member is member is already in Team");
            }
        } else {
            return ResponseEntity.ok().body("You are not admin");
        }
    }*/

    /*public boolean isAdminOrOwner(Team team) {
        // get the authenticated
        // check with temp 1
        User user = findUser(1L);
        return user.getOwn_teams().contains(team) || user.getAdmin_for_team().contains(team);
    }*/


}
