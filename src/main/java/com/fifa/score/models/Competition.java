package com.fifa.score.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_competition")
public class Competition {
    @Id
    private long id_competition;

    private String name;
    private String image;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Club> clubs;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "worldCup", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Country> countries;
}
