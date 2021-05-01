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

    public UserController(UserService userService) {
        this.userService = userService;
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
    /*@DeleteMapping("deleteTeam/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable long id) {
        return userService.deleteTeam(id);
    }*/

    @GetMapping("addMember")
    public ResponseEntity<String> addMemberInTeam(@RequestParam("user") long id_user, @RequestParam("team") long id_team) {
        return userService.addMemberInTeam(id_user, id_team);
    }

    @GetMapping("deleteMember")
    public ResponseEntity<String> deleteMember(@RequestParam("user") long id_user, @RequestParam("team") long id_team) {
        return userService.deleteMember(id_user, id_team);
    }

    @GetMapping("addAdmin")
    public ResponseEntity<String> SetAdminTeam(@RequestParam("user") long id_user, @RequestParam("team") long id_team) {
        return userService.addAdminInTeam(id_user, id_team);
    }

    @GetMapping("deleteAdmin")
    public ResponseEntity<String> deleteAdmin(@RequestParam("user") long id_user, @RequestParam("team") long id_team) {
        return userService.deleteAdmin(id_user, id_team);
    }

    @GetMapping("leaveTeam")
    public ResponseEntity<String> leaveTeam(@RequestParam("team") long id_team) {
        return userService.leaveTeam(id_team);
    }
}