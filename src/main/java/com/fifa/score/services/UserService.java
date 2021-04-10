package com.fifa.score.services;

import com.fifa.score.models.Competition;
import com.fifa.score.models.Team;
import com.fifa.score.models.User;
import com.fifa.score.repositories.TeamRepository;
import com.fifa.score.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ResponseEntity<String> deleteUser (User user){
        userRepository.delete(user);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    public ResponseEntity<String> createGroup(Team team){
        // get the authenticated user
        // temporary use temp user :-)
        User user_temp = findUser(1L);
        team.setOwner(user_temp);
        teamService.addTeam(team);


        return ResponseEntity.ok().body("Successfully created");
    }

}
