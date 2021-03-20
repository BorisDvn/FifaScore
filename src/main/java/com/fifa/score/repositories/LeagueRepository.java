package com.fifa.score.repositories;

import com.fifa.score.models.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    @Query("SELECT l.id_league FROM League l")
    List<Long> getlistofLeagueIds();

}

