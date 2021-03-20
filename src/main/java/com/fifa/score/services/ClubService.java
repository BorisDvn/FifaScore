package com.fifa.score.services;

import com.fifa.score.models.Club;
import com.fifa.score.models.League;
import com.fifa.score.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private RequestService requestService;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public ResponseEntity<String> addClub(Club club) {
        try {
            clubRepository.save(club);
            return ResponseEntity.ok().body("Successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error: Can't add this club");
        }
    }

    public ResponseEntity<String> addClub(List<Club> clubs) {
        try {
            clubRepository.saveAll(clubs);
            return ResponseEntity.ok().body("Successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error: Can't add this club");
        }
    }

    public ResponseEntity<String> initialisationClubFromLeague() {
        List<Long> competionIds = leagueService.getListOfLeagueIds();
        System.out.println(competionIds);
        try {
            ResponseEntity<Map> response = requestService.connectToFootApi("https://api.football-data.org/v2/competitions/2021/teams?season=2020");

            //Objects.requireNonNull(response);

            //assert response != null :"tp" ;
            //System.out.println("test1");

            @SuppressWarnings("unchecked")
            List<Object> clubApi = (List<Object>) Objects.requireNonNull(response.getBody()).get("teams");
            //assert clubApi != null;
            List<Club> clubs = new ArrayList<Club>();
            League l = new League();
            l.setId_league(2021);
            //System.out.println(response);
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
            return addClub(clubs);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
