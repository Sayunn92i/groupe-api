package com.controllers;

import com.dtos.ArtisteDto;

import org.springframework.web.bind.annotation.*;

import com.services.impl.ArtisteServiceImpl;


import java.util.List;

@RestController
@RequestMapping("/artistes")
public class ArtisteController {

    private final ArtisteServiceImpl artisteService;

    public ArtisteController(ArtisteServiceImpl artisteService) {
        this.artisteService = artisteService;
    }

    /**
     * <p>Get all artistes in the system</p>
     * @return List<ArtisteDto>
     */
    @GetMapping
    public List<ArtisteDto> getArtistes() {
        return artisteService.getAllArtistes();
    }

    /**
     * Method to get the artiste based on the ID
     */
    @GetMapping("/{id}")
    public ArtisteDto getArtiste(@PathVariable Long id){
        return artisteService.getArtisteById(id);
    }

    /**
     *
     * Cherche les artistes d'un groupe
     */
    @GetMapping("/groupes/{groupeId}")
    public List<ArtisteDto> getArtistesByGroupeId(@PathVariable Long groupeId) {
        return artisteService.getArtistesByGroupeId(groupeId);
    }
    /**
     * Create a new Artiste in the system
     */
    @PostMapping
    public ArtisteDto saveArtiste(final @RequestBody ArtisteDto artisteDto){
        return artisteService.saveArtiste(artisteDto);
    }

    /**
     * Delete an artiste by it's id
     */
    @DeleteMapping("/{id}")
    public boolean deleteArtiste(@PathVariable Long id){
        return artisteService.deleteArtiste(id);
    }
    /**
     * Update an artiste by it's id
     */
    @PutMapping("/{id}")
    public ArtisteDto updateArtiste(@PathVariable Long id, @RequestBody ArtisteDto artisteDto) {
        return artisteService.updateArtiste(id,artisteDto);
    }

}

