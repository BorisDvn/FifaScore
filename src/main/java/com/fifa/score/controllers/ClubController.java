package com.fifa.score.controllers;

import com.fifa.score.models.Club;
import com.fifa.score.models.League;
import com.fifa.score.services.ClubService;
import com.fifa.score.services.ResquestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/score/v1/clubController")
public class ClubController {

    private final ClubService clubService;

    private final ResquestService resquestService;


    public ClubController(ClubService clubService, ResquestService resquestService) {
        this.clubService = clubService;
        this.resquestService = resquestService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationClub() {

        try {
            ResponseEntity<Map> response = resquestService.connectToFootApi("https://api.football-data.org/v2/competitions/2021/teams?season=2020");
            assert response != null;

            @SuppressWarnings("unchecked")
            List<Object> clubApi = (List<Object>) Objects.requireNonNull(response.getBody()).get("teams");
            assert clubApi != null;
            List<Club> clubs = new ArrayList<Club>();
            League l = new League();
            l.setId_league(2021);

            for (int i = 0; i < clubApi.size(); i++) {
                Club club = new Club();

                @SuppressWarnings("unchecked")
                Map<String, Object> clubFromApi = (Map<String, Object>) clubApi.get(i);

                club.setId_club(Long.parseLong(String.valueOf(clubFromApi.get("id"))));
                club.setName((String) clubFromApi.get("name"));
                club.setImage((String) clubFromApi.get("crestUrl"));
                club.setLeague(l);
                clubs.add(club);
            }
            return clubService.addClub(clubs);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
