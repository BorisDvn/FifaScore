package com.fifa.score.services;

import com.fifa.score.models.League;
import com.fifa.score.repositories.LeagueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final RequestService requestService;

    public LeagueService(LeagueRepository leagueRepository, RequestService requestService) {
        this.leagueRepository = leagueRepository;
        this.requestService = requestService;
    }

    public ResponseEntity<String> addLeague(List<League> leagues) {
        try {
            leagueRepository.saveAll(leagues);
            return ResponseEntity.ok().body("Successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error: Can't add this club");
        }
    }

    public List<Long> getListOfLeagueIds() {
        return leagueRepository.getlistofLeagueIds();
    }

    public ResponseEntity<String> initialisationOfCompetition() {
        // List of 5 Leagues and World Cup
        Map<String, Integer> listeOfLeague = Stream.of(new Object[][]{
                {"Laliga", 2014}, {"SerieA", 2019}, {"PremierLeague", 2021},
                {"Bundesliga", 2002}, {"Ligue1", 2015}, {"World_Cup", 2000}
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        // for get from actual season
        //  int year = Year.now().getValue() - 1;

        List<League> leagues = new ArrayList<League>();
        listeOfLeague.forEach((k, v) -> {
            String url = "https://api.football-data.org/v2/competitions/" + v + "";

            ResponseEntity<Map> response = requestService.connectToFootApi(url);
            @SuppressWarnings("unchecked")
            Map<String, Object> competition = Objects.requireNonNull(response.getBody());
            // System.out.println(competition);

            // When internet is low
            //try {
            //    TimeUnit.SECONDS.sleep(1);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            League league = new League();

            league.setId_league(Long.parseLong(String.valueOf(competition.get("id"))));
            league.setImage((String) competition.get("emblemUrl"));
            league.setName((String) competition.get("name"));

            leagues.add(league);
        });
        addLeague(leagues);
        return ResponseEntity.ok().body("Successfully initialised");
    }

}
