package com.fifa.score.services;

import com.fifa.score.models.Club;
import com.fifa.score.repositories.ClubRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

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
        return null;
    }
}
