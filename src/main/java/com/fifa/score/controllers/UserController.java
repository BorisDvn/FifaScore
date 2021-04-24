package com.fifa.score.controllers;

import com.fifa.score.models.Team;
import com.fifa.score.models.User;
import com.fifa.score.services.TeamService;
import com.fifa.score.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score/v1/user/")
public class UserController {

    private final UserService userService;
    private final TeamService teamService;

    public UserController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable long id){
        return userService.findUser(id);
    }


    @PostMapping("addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("createTeam")
    public ResponseEntity<String> createTeam(@RequestBody Team team) {
        return userService.createTeam(team);
    }

    // mais il doit seulement pouvoir supprimer son Team
    // du coup il faut une verification
    @DeleteMapping("deleteTeam/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable long id) {
        return userService.deleteTeam(id);
    }

    /*@PostMapping("addMember")
    public ResponseEntity<String> addMemberInTeam(@RequestParam("user") long id_user, @RequestParam("team") long id_team) {
        return userService.addMemberInTeam(id_user, id_team);
    }*/
}