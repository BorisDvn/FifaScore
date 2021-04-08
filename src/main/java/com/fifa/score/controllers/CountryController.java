package com.fifa.score.controllers;

import com.fifa.score.models.Country;
import com.fifa.score.services.ClubService;
import com.fifa.score.services.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/score/v1/country/")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("initialisation")
    public ResponseEntity<String> initialisationCountryFromApi() {
        return countryService.initialisationCountries();
    }

    @GetMapping("countries")
    public List<Country> getAllCountries() {
        return countryService.findAllCountry();
    }
}
