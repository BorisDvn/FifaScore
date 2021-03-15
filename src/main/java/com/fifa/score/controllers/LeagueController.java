package com.fifa.score.controllers;

import com.fifa.score.services.LeagueService;
import com.fifa.score.services.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.Map;
import java.util.Objects;
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
                {"Bundesliga", 2002}, {"Ligue1", 2015}, {"Country", 2000}
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        // for get from actual season
        int year = Year.now().getValue() - 1;

        listeOfLeague.forEach((k, v) -> {
            String url = null;
            if (v == 2000) {
                // Fifa Word Cup 2018
                // url has not parameter season for word cup
                url = "https://api.football-data.org/v2/competitions/" + 2000 + "/teams";
            } else {
                url = "https://api.football-data.org/v2/competitions/" + v + "/teams?season=" + year + "";
            }

            ResponseEntity<Map> response = requestService.connectToFootApi(url);
            //System.out.println(response);
            @SuppressWarnings("unchecked")
            Map<String, Object> competition = Objects.requireNonNull(response.getBody());
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

