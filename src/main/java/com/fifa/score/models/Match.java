/*package com.fifa.score.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_match")
public class Match {

    @Id
    private long id_match;

    private String score;
    private LocalDateTime date;
    private String lieu;


    //private Club club_home;
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // private Map<Club,String> msap;
    //Map<Integer,String> map=new HashMap<Integer,String>();
    private List<Club> club1;

}*/
