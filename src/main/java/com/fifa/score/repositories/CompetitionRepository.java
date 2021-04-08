package com.fifa.score.repositories;

import com.fifa.score.models.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query("SELECT c.id_competition FROM Competition c")
    List<Long> getListOfCompetitionIds();

}

