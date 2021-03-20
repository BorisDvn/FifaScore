package com.fifa.score.services;

import com.fifa.score.models.League;
import com.fifa.score.repositories.LeagueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
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

    public League getLeague(Long id) {
        return leagueRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<League> findAll() {
        return leagueRepository.findAll();
    }

    public List<Long> getListOfLeagueIds() {
        return leagueRepository.getlistofLeagueIds();
    }

    public void addLeague(League league) {
        leagueRepository.save(league);
    }

    public void addLeague(List<League> leagues) {
        leagueRepository.saveAll(leagues);
    }


    public ResponseEntity<String> initialisationOfCompetition() {
        // List of 5 Leagues and World Cup
        Map<String, Integer> listeOfLeague = Stream.of(new Object[][]{
                {"Laliga", 2014}, {"SerieA", 2019}, {"PremierLeague", 2021},
                {"Bundesliga", 2002}, {"Ligue1", 2015}, {"World_Cup", 2000}
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        // for get from actual season
        //  int year = Year.now().getValue() - 1;

        List<League> leagues = new ArrayList<>();
        listeOfLeague.forEach((k, v) -> {
            String url = "https://api.football-data.org/v2/competitions/" + v + "";

            @SuppressWarnings("rawtypes") ResponseEntity<Map> response = requestService.connectToFootApi(url);
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

    public ResponseEntity<String> updateLeague (long id, Map<String, Object> league) {
        League league1 = getLeague(id);

        league.forEach((element, value) -> {
            switch (element) {
                case "name":
                    league1.setName((String) value);
                    break;
                case "description":
                    league1.setImage((String) value);
                    break;
            }
        });

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<League>> violations = validator.validate(league1);

        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.toString());
        }

        addLeague(league1);
        return ResponseEntity.ok().body("Successfully updated");
    }

}
