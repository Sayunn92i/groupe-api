package com.services;

import com.dtos.GroupeDto;

import java.util.List;

public interface GroupeService {
    /**
     * Sauve a groupe
     */
    GroupeDto saveGroupe(GroupeDto groupeDto);

    /**
     * Get a groupe by it's id
     */
    GroupeDto getGroupeById(Long groupeId);

    /**
     * Delete a groupe by it's id
     */
    boolean deleteGroupe(Long groupeId);

    /**
     * Get all the groupes
     */
    List<GroupeDto> getAllGroupes();

    GroupeDto updateGroupe(Long groupeId, GroupeDto groupeDto);


}
