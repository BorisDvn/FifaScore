package com.fifa.score.services;

import com.fifa.score.models.Club;
import com.fifa.score.models.Competition;
import com.fifa.score.repositories.ClubRepository;
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

    private final CompetitionService competitionService;
    private final RequestService requestService;

    public ClubService(ClubRepository clubRepository, CompetitionService competitionService, RequestService requestService) {
        this.clubRepository = clubRepository;
        this.competitionService = competitionService;
        this.requestService = requestService;
    }

    public void addClub(List<Club> clubs) {
        clubRepository.saveAll(clubs);
    }

    public ResponseEntity<String> initialisationClubFromLeague() {
        List<Long> competitionIds = competitionService.listOfCompetitionIds();
        // remove world cup
        competitionIds.remove(Long.valueOf(2000));

        // actual season
        int year = Year.now().getValue() - 1;
        ResponseEntity<Map> response;

        try {
            for (Long competition_id : competitionIds) {
                response = requestService.connectToFootApi("https://api.football-data.org/v2/competitions/" + competition_id + "/teams?season=" + year + "");

                @SuppressWarnings({"unchecked"})
                List<Object> clubApi = (List<Object>) Objects.requireNonNull(response.getBody()).get("teams");
                List<Club> clubs = new ArrayList<>();
                Competition competition = new Competition();
                competition.setId_competition(competition_id);

                for (Object o : clubApi) {
                    Club club = new Club();

                    @SuppressWarnings("unchecked")
                    Map<String, Object> clubFromApi = (Map<String, Object>) o;

                    club.setId_club(Long.parseLong(String.valueOf(clubFromApi.get("id"))));
                    club.setName((String) clubFromApi.get("name"));
                    club.setImage((String) clubFromApi.get("crestUrl"));
                    club.setLeague(competition);
                    clubs.add(club);
                }
                addClub(clubs);
            }
            return ResponseEntity.ok().body("Successfully initialised");
        } catch (Exception e) {
            //System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
