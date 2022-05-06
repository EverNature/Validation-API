package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Specie;

public interface SpecieRepository extends JpaRepository<Specie, Integer> {
    
}
