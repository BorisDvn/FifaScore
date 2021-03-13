package com.fifa.score.controllers;

import com.fifa.score.models.Club;
import com.fifa.score.models.League;
import com.fifa.score.services.ClubService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/clubController")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationClub() {
        final String url = "https://api.football-data.org/v2/competitions/2021/teams?season=2020";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "1856b1a8efff4d118603a3a889e93e66");
        //headers.set();

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // make an HTTP GET request with headers
        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Map.class
        );

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            // System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        try {
            List<Object> clubApi = (List<Object>) Objects.requireNonNull(response.getBody()).get("teams");
            assert clubApi != null;
            List<Club> clubs = new ArrayList<Club>();
            League l = new League();
            l.setId_league(2021);

            for (int i = 0; i < clubApi.size(); i++) {
                Club club = new Club();

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
