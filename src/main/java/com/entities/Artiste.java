package com.entities;

import java.sql.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Artiste {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="art_id")
    private Long id;
    @Column(name="art_nom")
    private String nom;
    @Column(name="art_prenom")
    private String prenom;
    @Column(name="art_date_naissance")
    private Date date_naissance;
    @Column(name="art_ville_origine")
    private String ville_origine;

    @ManyToOne
    @JoinColumn(name = "grp_id")
    private Groupe groupe;

}

