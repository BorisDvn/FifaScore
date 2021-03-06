package com.fifa.score.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_team;

    @Lob
    private String description;
    private String name;
    private LocalDate creation_date;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;


    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(targetEntity = User.class, mappedBy = "teams", cascade = CascadeType.ALL)
    List<User> members;

    /*@JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(targetEntity = User.class, mappedBy = "admin_for_team", cascade = CascadeType.ALL)
    List<User> administrators;*/
}
