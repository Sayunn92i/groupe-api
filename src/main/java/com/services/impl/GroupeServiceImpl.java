package com.services.impl;


import com.dtos.GroupeDto;
import com.entities.Artiste;
import com.entities.Groupe;
import com.repositories.GroupeRepository;
import com.repositories.ArtisteRepository;

import com.services.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service("groupeService")
public class GroupeServiceImpl implements GroupeService {

    private final GroupeRepository groupeRepository;
    @Autowired
    private ArtisteRepository artisteRepository;

    public GroupeServiceImpl(GroupeRepository groupeRepository){
        this.groupeRepository = groupeRepository;
    }

    @Override
    public GroupeDto saveGroupe(GroupeDto groupeDto) {
        // Converts the groupe dto to the groupe entity
        Groupe groupe = groupeDtoToEntity(groupeDto);
        // Save the groupe entity
        groupe = groupeRepository.save(groupe);
        groupe.updateSeul();
        // Return the new dto
        return groupeEntityToDto(groupe);
    }

    @Override
    public GroupeDto getGroupeById(Long groupeId) {
        Groupe groupe = groupeRepository.findById(groupeId).orElseThrow(() -> new EntityNotFoundException("Groupe not found"));
        return groupeEntityToDto(groupe);
    }

    @Override
    public boolean deleteGroupe(Long groupeId) {
        GroupeDto groupeDto = getGroupeById(groupeId);

        if (groupeDto != null) {
            if (groupeDto.getArtistes() != null) {
                for (Long artisteId : groupeDto.getArtistes()) {
                    Artiste artiste = artisteRepository.findById(artisteId).orElseThrow(() -> new EntityNotFoundException("Artiste not found"));
                    artiste.setGroupe(null);
                    artisteRepository.save(artiste);
                }
            }
            groupeRepository.deleteById(groupeId);
            return true;
        } else {
            return false;
        }


    }
    public GroupeDto updateGroupe(Long groupeId, GroupeDto groupeDto) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new NoSuchElementException("Le groupe avec l'ID " + groupeId + " n'existe pas."));

        // Mettre à jour les propriétés du groupe
        groupe.setNom(groupeDto.getNom());
        groupe.setSeul(groupeDto.getSeul());

        // Mettre à jour la liste des artistes du groupe
        List<Long> artisteIds = groupeDto.getArtistes();
        if (artisteIds != null) {
            List<Artiste> artistes = artisteRepository.findAllById(artisteIds);
            groupe.setArtistes(artistes);
        }

        // Enregistrer les modifications dans la base de données
        groupe = groupeRepository.save(groupe);

        // Convertir l'entité Groupe en DTO GroupeDto
        return groupeEntityToDto(groupe);
    }


    @Override
    public List<GroupeDto> getAllGroupes() {
        List<GroupeDto> groupeDtos = new ArrayList<>();
        List<Groupe> groupes = groupeRepository.findAll();
        groupes.forEach(groupe -> groupeDtos.add(groupeEntityToDto(groupe)));
        return groupeDtos;
    }

    /**
     * Map groupe dto to groupe entity
     */
    private GroupeDto groupeEntityToDto(Groupe groupe){
        GroupeDto groupeDto = new GroupeDto();
        groupeDto.setId(groupe.getId());
        groupeDto.setNom(groupe.getNom());
        groupeDto.setSeul(groupe.getSeul());
        List<Long> artisteIds = groupe.getArtistes()
                .stream()
                .map(Artiste::getId)
                .collect(Collectors.toList());
        groupeDto.setArtistes(artisteIds);
        return groupeDto;
    }

    /**
     * Map groupe entity to groupe dto
     */
    private Groupe groupeDtoToEntity(GroupeDto groupeDto){
        Groupe groupe = new Groupe();
        groupe.setId(groupeDto.getId());
        groupe.setNom(groupeDto.getNom());
        groupe.setSeul(groupeDto.getSeul());
        List<Artiste> artistes = new ArrayList<>();
        if (groupeDto.getArtistes() != null) {
            for (Long artisteId : groupeDto.getArtistes()) {
                Artiste artiste = artisteRepository.findById(artisteId)
                        .orElseThrow(() -> new NoSuchElementException("Artiste non trouvé avec l'ID: " + artisteId));
                artistes.add(artiste);
            }
        }
        groupe.setArtistes(artistes);
        return groupe;
    }

}
