package com.fifa.score.services;

import com.fifa.score.models.Competition;
import com.fifa.score.repositories.CompetitionRepository;
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
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final RequestService requestService;

    public CompetitionService(CompetitionRepository competitionRepository, RequestService requestService) {
        this.competitionRepository = competitionRepository;
        this.requestService = requestService;
    }

    public Competition getCompetition(Long id) {
        return competitionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public List<Long> ListOfCompetitionIds() {
        return competitionRepository.getListOfCompetitionIds();
    }

    public void addCompetition(Competition competition) {
        competitionRepository.save(competition);
    }

    public void addCompetition(List<Competition> competitions) {
        competitionRepository.saveAll(competitions);
    }


    public ResponseEntity<String> initialisationOfCompetition() {
        // List of 5 Leagues and World Cup
        Map<String, Integer> listOfCompetition = Stream.of(new Object[][]{
                {"Laliga", 2014}, {"SerieA", 2019}, {"PremierLeague", 2021},
                {"Bundesliga", 2002}, {"Ligue1", 2015}, {"World_Cup", 2000}
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        List<Competition> competitions = new ArrayList<>();
        listOfCompetition.forEach((k, v) -> {
            String url = "https://api.football-data.org/v2/competitions/" + v + "";

            @SuppressWarnings("rawtypes") ResponseEntity<Map> response = requestService.connectToFootApi(url);
            @SuppressWarnings("unchecked")
            Map<String, Object> competitions_obj = Objects.requireNonNull(response.getBody());

            // When internet is low
            //try {
            //    TimeUnit.SECONDS.sleep(1);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}

            Competition competition = new Competition();
            competition.setId_competition(Long.parseLong(String.valueOf(competitions_obj.get("id"))));
            competition.setImage((String) competitions_obj.get("emblemUrl"));
            competition.setName((String) competitions_obj.get("name"));

            competitions.add(competition);
        });
        addCompetition(competitions);
        return ResponseEntity.ok().body("Successfully initialised");
    }

    public ResponseEntity<String> updateLeague(long id, Map<String, Object> competition) {
        Competition competition_temp = getCompetition(id);

        competition.forEach((element, value) -> {
            switch (element) {
                case "name":
                    competition_temp.setName((String) value);
                    break;
                case "description":
                    competition_temp.setImage((String) value);
                    break;
            }
        });

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Competition>> violations = validator.validate(competition_temp);

        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.toString());
        }

        addCompetition(competition_temp);
        return ResponseEntity.ok().body("Successfully updated");
    }

}
