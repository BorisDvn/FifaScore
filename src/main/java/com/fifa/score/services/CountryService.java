package com.fifa.score.services;

import com.fifa.score.models.Competition;
import com.fifa.score.models.Country;
import com.fifa.score.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RequestService requestService;

    public Country getCountry(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Country> getAllCountry() {
        return countryRepository.findAll();
    }

    public void addCountry(Country country) {
        countryRepository.save(country);
    }

    public void addCountry(List<Country> countries) {
        countryRepository.saveAll(countries);
    }

    public ResponseEntity<String> initialisationCountries() {
        ResponseEntity<Map> response;

        try {
            response = requestService.connectToFootApi("https://api.football-data.org/v2/competitions/2000/teams");

            @SuppressWarnings("unchecked")
            List<Object> countriesApi = (List<Object>) Objects.requireNonNull(response.getBody()).get("teams");
            List<Country> countries = new ArrayList<>();
            Competition competition = new Competition();
            competition.setId_competition(2000);

            for (Object c : countriesApi) {
                Country country = new Country();

                @SuppressWarnings("unchecked")
                Map<String, Object> countryFromApi = (Map<String, Object>) c;

                country.setId_country(Long.parseLong(String.valueOf(countryFromApi.get("id"))));
                country.setName((String) countryFromApi.get("name"));
                country.setImage((String) countryFromApi.get("crestUrl"));
                country.setWorldCup(competition);
                countries.add(country);
            }
            addCountry(countries);

            return ResponseEntity.ok().body("Successfully initialised");
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
