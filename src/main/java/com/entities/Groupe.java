package com.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Groupe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grp_id")
    private Long id;
    @Column(name="grp_nom")
    private String nom;
    @Column(name="grp_seul")
    private  Boolean seul;

    @OneToMany(mappedBy = "groupe")
    private List<Artiste> artistes;

    public void updateSeul() { //Si un groupe contient un seul artiste le bool seul est mis à true
        if(artistes.size() == 1) {
            this.setSeul(true);
        } else {
            this.setSeul(false);
        }
    }

}
