package com.fifa.score.controllers;

import com.fifa.score.services.LeagueService;
import com.fifa.score.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/score/v1/leagueController")
public class LeagueController {
    private final LeagueService leagueService;

    private final RequestService requestService;

    public LeagueController(LeagueService leagueService, RequestService requestService) {
        this.leagueService = leagueService;
        this.requestService = requestService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationClub() {

        Map<String, Integer> listeOfLeague = Stream.of(new Object[][]{
                {"Laliga", 2014}, {"SerieA", 2019}, {"PremierLeague", 2021},
                {"Bundesliga", 2002}, {"Ligue1", 2015}
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        //, {"Country", 2000}
        listeOfLeague.forEach((k, v) -> {
            //String url = "https://api.football-data.org/v2/competitions/" + 2000 + "/teams?season=2020";
            // Fifa Word Cup 2018
            String url = "https://api.football-data.org/v2/competitions/" + 2000 + "/teams";
            ResponseEntity<Map> response = requestService.connectToFootApi(url);
            System.out.println(response);
            @SuppressWarnings("unchecked")
            Map<String, Object> competition = Objects.requireNonNull(response.getBody());
            System.out.println(competition);
            // When internet is low
            //try {
            //    TimeUnit.SECONDS.sleep(2);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}

        });

        return null;
    }

}

