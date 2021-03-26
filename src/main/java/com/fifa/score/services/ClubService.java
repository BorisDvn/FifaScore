package com.fifa.score.services;

import com.fifa.score.models.Club;
import com.fifa.score.models.League;
import com.fifa.score.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Year;
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

    public void addClub(List<Club> clubs) {
        clubRepository.saveAll(clubs);
    }

    public ResponseEntity<String> initialisationClubFromLeague() {
        List<Long> competionIds = leagueService.getListOfLeagueIds();

        // actual season
        int year = Year.now().getValue() - 1;
        ResponseEntity<Map> response;

        try {
            for (Long idLeague : competionIds) {
                if (idLeague == 2000) {
                    response = requestService.connectToFootApi("https://api.football-data.org/v2/competitions/" + idLeague + "/teams");
                } else {
                    response = requestService.connectToFootApi("https://api.football-data.org/v2/competitions/" + idLeague + "/teams?season=" + year + "");
                }

                @SuppressWarnings("unchecked")
                List<Object> clubApi = (List<Object>) Objects.requireNonNull(response.getBody()).get("teams");
                List<Club> clubs = new ArrayList<>();
                League l = new League();
                l.setId_league(idLeague);

                for (Object o : clubApi) {
                    Club club = new Club();

                    @SuppressWarnings("unchecked")
                    Map<String, Object> clubFromApi = (Map<String, Object>) o;

                    club.setId_club(Long.parseLong(String.valueOf(clubFromApi.get("id"))));
                    club.setName((String) clubFromApi.get("name"));
                    club.setImage((String) clubFromApi.get("crestUrl"));
                    club.setLeague(l);
                    clubs.add(club);
                }
                addClub(clubs);
            }
            return ResponseEntity.ok().body("Successfully initialised");
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
