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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_league")
public class League {
    @Id
    private long id_league;

    private String name;
    private String image;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Club> clubs;
}
