package com.services.impl;

import com.dtos.ArtisteDto;

import com.entities.Artiste;
import com.entities.Groupe;
import com.repositories.ArtisteRepository;
import com.repositories.GroupeRepository; /*Pour utiliser les methodes pour obtenir l'id d'un groupe*/
import com.services.ArtisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("artisteService")
public class ArtisteServiceImpl implements ArtisteService {

    private final ArtisteRepository artisteRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    public ArtisteServiceImpl(ArtisteRepository artisteRepository){
        this.artisteRepository = artisteRepository;
    }

    @Override
    public ArtisteDto saveArtiste(ArtisteDto artisteDto) {
        // Converts the dto to the artiste entity
        Artiste artiste = artisteDtoToEntity(artisteDto);
        // Save the artiste entity
        artiste = artisteRepository.save(artiste);
        // Return the new artiste
        return artisteEntityToDto(artiste);
    }

    @Override
    public ArtisteDto getArtisteById(Long art_id) {
        Artiste artiste = artisteRepository.findById(art_id).orElseThrow(() -> new EntityNotFoundException("Artiste not found"));
        return artisteEntityToDto(artiste);
    }

    @Override
    public boolean deleteArtiste(Long artisteId) {
        ArtisteDto artisteDto = getArtisteById(artisteId);
        if (artisteDto != null) {
            if (artisteDto.getGroupeId() != null) {
                // Retirer l'artiste du groupe
                Groupe groupe = groupeRepository.getById(artisteDto.getGroupeId());

                groupe.getArtistes().remove(artisteId);
                groupeRepository.save(groupe);

            }


            artisteRepository.deleteById(artisteId);
            return true;

        } else {
            return false;
        }
    }

    public ArtisteDto updateArtiste(Long artisteId, ArtisteDto artisteDto) {
        Artiste artiste = artisteRepository.findById(artisteId)
                .orElseThrow(() -> new NoSuchElementException("L'artiste avec l'ID " + artisteId + " n'existe pas."));

        // Mettre à jour les propriétés de l'artiste
        artiste.setNom(artisteDto.getNom());
        artiste.setPrenom(artisteDto.getPrenom());
        artiste.setDate_naissance(artisteDto.getDate_naissance());
        artiste.setVille_origine(artisteDto.getVille_origine());

        // Mettre à jour l'appartenance du groupe de l'artiste
        Long groupeId = artisteDto.getGroupeId();
        if (groupeId != null) {
            Groupe groupe = groupeRepository.findById(groupeId)
                    .orElseThrow(() -> new NoSuchElementException("Le groupe avec l'ID " + groupeId + " n'existe pas."));
            artiste.setGroupe(groupe);
        } else {
            artiste.setGroupe(null);
        }

        // Enregistrer les modifications dans la base de données
        artiste = artisteRepository.save(artiste);

        // Convertir l'entité Artiste en DTO ArtisteDto
        return artisteEntityToDto(artiste);
    }
    @Override
    public List<ArtisteDto> getAllArtistes() {
        List<ArtisteDto> artisteDtos = new ArrayList<>();
        List<Artiste> artistes = artisteRepository.findAll();
        artistes.forEach(artiste -> artisteDtos.add(artisteEntityToDto(artiste)));
        return artisteDtos;
    }
    public List<ArtisteDto> getArtistesByGroupeId(Long grp_id){
        List<ArtisteDto> artisteDtos = new ArrayList<>();
        List<Artiste> artistes = artisteRepository.findByGroupeId(grp_id);
        artistes.forEach(artiste -> artisteDtos.add(artisteEntityToDto(artiste)));
        return artisteDtos;
    }

    /**
     * Map artiste dto to artiste entity
     */
    public ArtisteDto artisteEntityToDto(Artiste artiste){
        ArtisteDto artisteDto = new ArtisteDto();
        artisteDto.setId(artiste.getId());
        artisteDto.setNom(artiste.getNom());
        artisteDto.setPrenom(artiste.getPrenom());
        artisteDto.setDate_naissance(artiste.getDate_naissance());
        artisteDto.setVille_origine(artiste.getVille_origine());
        artisteDto.setGroupeId(artiste.getGroupe() != null ? artiste.getGroupe().getId() : null);
        return artisteDto;
    }

    /**
     * Map artiste entity to artiste dto
     */
    private Artiste artisteDtoToEntity(ArtisteDto artisteDto){
        Artiste artiste = new Artiste();
        artiste.setId(artisteDto.getId());
        artiste.setNom(artisteDto.getNom());
        artiste.setPrenom(artisteDto.getPrenom());
        artiste.setDate_naissance(artisteDto.getDate_naissance());
        artiste.setVille_origine(artisteDto.getVille_origine());
        Long groupeId = artisteDto.getGroupeId();
        if (groupeId != null) {
            Optional<Groupe> optionalGroupe = groupeRepository.findById(groupeId);
            if (optionalGroupe.isPresent()) {
                Groupe groupe = optionalGroupe.get();
                artiste.setGroupe(groupe);
            } else {
                // Si le groupe n'existe pas, vous pouvez jeter une exception ou gérer l'erreur de votre choix
                throw new EntityNotFoundException("Le groupe avec l'identifiant " + groupeId + " n'existe pas");
            }
        }
        return artiste;
    }
}
