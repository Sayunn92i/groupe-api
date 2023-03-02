package com.services;

import com.dtos.ArtisteDto;
import com.entities.Artiste;
import com.repositories.ArtisteRepository;

import java.util.List;

public interface ArtisteService {
    /**
     * Sauve a dog
     */
    ArtisteDto saveArtiste(ArtisteDto artisteDto);

    /**
     * Get a dog by it's id
     */
    ArtisteDto getArtisteById(Long artisteId);

    /**
     * Delete a dog by it's id
     */
    boolean deleteArtiste(Long artisteId);

    /**
     * Get all the artistes
     */
    List<ArtisteDto> getAllArtistes();
    List<ArtisteDto> getArtistesByGroupeId(Long grp_id);
    ArtisteDto artisteEntityToDto(Artiste artiste);
    /**
     * Update an artiste
     */

    ArtisteDto updateArtiste(Long artisteId, ArtisteDto artisteDto);
}

