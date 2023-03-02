package com.dtos;

import com.entities.Groupe;
import lombok.Data;

import java.sql.Date;


@Data
public class ArtisteDto {

    private Long id;
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String ville_origine;
    private Long groupeId;



}
