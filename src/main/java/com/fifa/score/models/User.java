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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;

    String nom;
    String prenoms;
    String email;
    // String password;
    // String rolle;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Team> own_teams;

    // Team wo he is member
   @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(targetEntity = Team.class,cascade = CascadeType.ALL )
    List<Team> teams;

     /*// Team wo he is admin
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(targetEntity = Team.class,cascade = CascadeType.ALL )
    List<Team> admin_for_team;*/

}
