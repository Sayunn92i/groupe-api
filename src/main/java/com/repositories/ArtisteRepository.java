package com.repositories;

import com.entities.Artiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtisteRepository extends JpaRepository<Artiste, Long> {
    List<Artiste> findByGroupeId(Long groupeId);
}
