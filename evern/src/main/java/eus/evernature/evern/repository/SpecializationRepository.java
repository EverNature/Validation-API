package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    
}
